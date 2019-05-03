package com.dematte.waestest.repositories;

import com.dematte.waestest.model.entities.Record;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface representing the {@link Record} repository
 *
 * @author Rodolfo Chiuff Dematte
 * @since 2019-05-01
 */
@Repository
public interface RecordRepository extends CrudRepository<Record, String> {}
