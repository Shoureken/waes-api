package com.dematte.waestest.services;

import com.dematte.waestest.model.Side;
import com.dematte.waestest.model.entities.Record;

/**
 * Interface that expose RecordService contract to manage Records
 *
 * @author Rodolfo Chiuff Dematte
 * @since 2019-05-01
 */
public interface RecordService {

	/**
	 * Create or update {@link Record} data
	 *
	 * @param id    Record identification to save
	 * @param side  Each side data has to saved, may be "left" or "right"
	 * @param value Binary data to be stored
	 * @return {@link Record} saved
	 * @throws com.dematte.waestest.exception.InvalidData If data is not a JSON Base64 encoded
	 */
	Record save(String id, Side side, byte[] value);

	/**
	 * Retrieve {@link Record} base on id
	 *
	 * @param id Record identification
	 * @return {@link Record} found
	 * @throws com.dematte.waestest.exception.RecordNotFoundException If no {@link Record} was found
	 */
	Record get(String id);

}
