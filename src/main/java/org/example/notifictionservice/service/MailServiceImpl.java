package org.example.notifictionservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.notifictionservice.data.MailData;
import org.example.notifictionservice.data.enums.MailStatus;
import org.example.notifictionservice.messaging.EmailMessage;
import org.example.notifictionservice.repository.MailRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Instant;

/**
 * Service implementation for mail sending.
 * Handles processing of new emails, sending attempts, and retry logic.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;
    private final MailRepository mailRepository;

    @Value("${mail.retry.maxAttempts}")
    private int maxAttempts;

    /**
     * Processes a new incoming email message.
     * Converts the DTO to a MailData entity, saves it, and attempts to send.
     *
     * @param emailMessage the incoming email message DTO
     */
    @Override
    public void processNewMail(EmailMessage emailMessage) {
        MailData mailData = convertEmailMessageToMailData(emailMessage);
        mailRepository.save(mailData);
        attemptToSend(mailData);
    }

    /**
     * Attempts to send an email. Updates status (SEND or ERROR) depending on success or failure.
     * If the number of attempts exceeds maxAttempts, marks the mail as FAILED.
     *
     * @param mailData the mail entity to send
     */
    @Override
    public void attemptToSend(MailData mailData) {
        if (mailData.getAttemptCount() >= maxAttempts) {
            log.info("Mail {} exceeded max retry attempts", mailData.getId());
            mailData.setStatus(MailStatus.FAILED);
            mailData.setErrorMessage("Exceeded max retry attempts");
            mailRepository.save(mailData);
            return;
        }

        try {
            SimpleMailMessage message = createSimpleMailMessage(mailData);
            mailSender.send(message);
            mailData.setStatus(MailStatus.SEND);
            mailData.setErrorMessage(null);
            log.info("Mail {} sent successfully to {}", mailData.getId(), mailData.getRecipientsEmails());
        } catch (Exception ex) {
            mailData.setStatus(MailStatus.ERROR);
            mailData.setErrorMessage(ex.getClass().getSimpleName() + ": " + ex.getMessage());
            mailData.setAttemptCount(mailData.getAttemptCount() + 1);
            log.warn("Failed to send mail {}. Attempt {}. Error: {}",
                    mailData.getId(), mailData.getAttemptCount(), ex.getMessage(), ex);
        } finally {
            mailData.setLastAttempt(Instant.now());
            mailRepository.save(mailData);
        }
    }

    private SimpleMailMessage createSimpleMailMessage(MailData mailData) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailData.getRecipientsEmails().toArray(new String[0]));
        message.setSubject(mailData.getSubject());
        message.setText(mailData.getContent());
        System.out.println("message: " + message);
        return message;
    }

    private MailData convertEmailMessageToMailData(EmailMessage dto) {
        return MailData.builder()
                .subject(dto.getSubject())
                .content(dto.getContent())
                .recipientsEmails(dto.getRecipientsEmails())
                .status(MailStatus.NEW)
                .attemptCount(1)
                .lastAttempt(Instant.now())
                .build();
    }
}