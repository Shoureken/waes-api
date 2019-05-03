package com.dematte.waestest.services;

import com.dematte.waestest.exception.MissingDataException;
import com.dematte.waestest.model.dto.DiffResult;
import com.dematte.waestest.model.dto.RecordDiffResult;
import com.dematte.waestest.model.entities.Record;
import com.dematte.waestest.services.impl.RecordDiffServiceImpl;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RecordDiffServiceTest {

	private RecordDiffService recordDiffService = new RecordDiffServiceImpl();

	@Test
	public void computeDiffEquals() {
		final byte[] left = "{\"data\" : 10}".getBytes();
		final Record record = getRecord(left, left);

		final RecordDiffResult result = recordDiffService.computeDiff(record);

		assertEquals(DiffResult.EQUAL, result.getResult());
	}

	@Test
	public void computeDiffDifferentLeght() {
		final byte[] left = "{\"dataaa\" : 10}".getBytes();
		final byte[] right = "{\"data\" : 10}".getBytes();
		final Record record = getRecord(left, right);

		final RecordDiffResult result = recordDiffService.computeDiff(record);

		assertEquals(DiffResult.DIFFERENT_LENGTH, result.getResult());
	}

	@Test
	public void computeDiffNotEquals() {
		final byte[] left = "{\"data\" : 11}".getBytes();
		final byte[] right = "{\"data\" : 10}".getBytes();
		final Record record = getRecord(left, right);

		final RecordDiffResult result = recordDiffService.computeDiff(record);

		assertEquals(DiffResult.NOT_EQUAL, result.getResult());
		assertEquals(1, result.getParts().size());

	}

	@Test(expected = MissingDataException.class)
	public void computeDiffMissingDataLeft() {
		final byte[] data = "{\"data\" : 10}".getBytes();
		final Record record = getRecord(null, data);
		recordDiffService.computeDiff(record);
	}

	@Test(expected = MissingDataException.class)
	public void computeDiffMissingDataRight() {
		final byte[] data = "{\"data\" : 10}".getBytes();
		final Record record = getRecord(data, null);
		recordDiffService.computeDiff(record);
	}

	private static Record getRecord(byte[] left, byte[] right) {
		return new Record("ID", left, right);
	}

}