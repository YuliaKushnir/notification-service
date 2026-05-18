package org.example.notifictionservice.service;

/**
 * Service interface for scheduled retry operations.
 */
public interface ScheduledSendingMessageService {
    void retryFailedEmails();
}
