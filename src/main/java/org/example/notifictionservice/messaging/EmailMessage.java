package org.example.notifictionservice.messaging;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

/**
 * Data Transfer Object representing an email message.
 * Used for sending notifications via RabbitMQ and for mail processing.
 */
@Data
@Builder
@Jacksonized
public class EmailMessage {
    private String subject;
    private String content;
    private List<String> recipientsEmails;
}
