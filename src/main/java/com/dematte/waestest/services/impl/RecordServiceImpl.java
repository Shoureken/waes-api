package com.dematte.waestest.services.impl;

import java.util.Base64;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

import com.dematte.waestest.exception.InvalidData;
import com.dematte.waestest.exception.RecordNotFoundException;
import com.dematte.waestest.model.Side;
import com.dematte.waestest.model.entities.Record;
import com.dematte.waestest.repositories.RecordRepository;
import com.dematte.waestest.services.RecordService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import static com.dematte.waestest.model.Side.LEFT;

@RequiredArgsConstructor
@Service
public class RecordServiceImpl implements RecordService {

	private final RecordRepository repository;

	@Override
	public Record save(final String id, final Side side, final byte[] value) {
		// Try to Base64 decode
		final byte[] decoded;
		try {
			decoded = decode(value);
		} catch (IllegalArgumentException e) {
			throw new InvalidData("Data can not be decoded", e);
		}

		// Check if is a valid JSON
		try {
			new JSONObject(new String(decoded));
		} catch (JSONException e) {
			throw new InvalidData("Data must be in JSON format", e);
		}

		// Check if we already have a record with same id
		final Optional<Record> optional = repository.findById(id);

		// Case we dont have, start new one
		final Record record = optional.orElse(new Record());
		record.setId(id);
		fillRecordData(record, side, decoded);

		// Persist the data
		return repository.save(record);
	}

	private static byte[] decode(byte[] bytes) {
		return Base64.getDecoder().decode(bytes);
	}

	/**
	 * Fill data according side parameter
	 *
	 * @param record Record to be filled
	 * @param side   Side to fill
	 * @param value  Data to be filled with
	 */
	private void fillRecordData(final Record record, final Side side, final byte[] value) {
		if (LEFT == side) {
			record.setLeft(value);
			return;
		}
		record.setRight(value);
	}

	@Override
	public Record get(final String id) {
		final Optional<Record> optional = repository.findById(id);
		return optional.orElseThrow(() -> new RecordNotFoundException("Record not found with id '" + id + "'"));
	}

}
