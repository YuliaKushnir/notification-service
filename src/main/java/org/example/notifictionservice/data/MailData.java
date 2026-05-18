package org.example.notifictionservice.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.notifictionservice.data.enums.MailStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;
import java.util.List;

/**
 * Entity representing mail data stored in Elasticsearch.
 */
@Getter
@Setter
@Builder
@Document(indexName = "emails")
public class MailData {

    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String subject;

    @Field(type = FieldType.Text)
    private String content;

    @Field(type = FieldType.Keyword)
    private List<String> recipientsEmails;

    @Field(type = FieldType.Keyword)
    private MailStatus status;

    @Field(type = FieldType.Text)
    private String errorMessage;

    @Field(type = FieldType.Integer)
    private int attemptCount;

    @Field(type = FieldType.Date)
    private Instant lastAttempt;
}

