package org.example.notifictionservice.repository;

import org.example.notifictionservice.data.MailData;
import org.example.notifictionservice.data.enums.MailStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repository interface for managing {@link MailData} entities in Elasticsearch.
 * Provides CRUD operations and custom queries for mail data.
 */
public interface MailRepository extends CrudRepository<MailData, String> {
    List<org.example.notifictionservice.data.MailData> findByStatus(MailStatus status);
}
