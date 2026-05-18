package org.example.notifictionservice.service;

import lombok.RequiredArgsConstructor;
import org.example.notifictionservice.data.MailData;
import org.example.notifictionservice.data.enums.MailStatus;
import org.example.notifictionservice.repository.MailRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of {@link ScheduledSendingMessageService}.
 * Periodically retries sending mails with ERROR status.
 */
@EnableScheduling
@Service
@RequiredArgsConstructor
public class ScheduledSendingMessageServiceImpl implements ScheduledSendingMessageService {

    private final MailRepository mailRepository;
    private final MailService mailService;

    /**
     * Retries sending mails with ERROR status every 5 minutes.
     * Uses {@link MailServiceImpl#attemptToSend(MailData)} for each failed mail.
     */
    @Override
    @Scheduled(fixedRateString = "${mail.retry.fixedRate}")
    public void retryFailedEmails() {
        List<MailData> failedMails = mailRepository.findByStatus(MailStatus.ERROR);
        failedMails.forEach(mailService::attemptToSend);
    }
}