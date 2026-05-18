package org.example.notifictionservice.service;


import org.example.notifictionservice.data.MailData;
import org.example.notifictionservice.messaging.EmailMessage;

/**
 * Service interface for mail operations.
 */
public interface MailService {
    void processNewMail(EmailMessage emailMessage);
    void attemptToSend(MailData mailData);
}