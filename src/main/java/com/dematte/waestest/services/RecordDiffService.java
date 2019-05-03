package com.dematte.waestest.services;

import com.dematte.waestest.model.dto.RecordDiffResult;
import com.dematte.waestest.model.entities.Record;

/**
 * Interface that expose RecordDiffService contract responsible for compute the difference
 * between data stored in each side of {@link Record}
 *
 * @author Rodolfo Chiuff Dematte
 * @since 2019-05-01
 */
public interface RecordDiffService {

	RecordDiffResult computeDiff(Record record);

}
