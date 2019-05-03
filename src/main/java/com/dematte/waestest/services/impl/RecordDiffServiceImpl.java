package com.dematte.waestest.services.impl;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;

import com.dematte.waestest.exception.MissingDataException;
import com.dematte.waestest.model.dto.DiffPart;
import com.dematte.waestest.model.dto.DiffResult;
import com.dematte.waestest.model.dto.RecordDiffResult;
import com.dematte.waestest.model.entities.Record;
import com.dematte.waestest.services.RecordDiffService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RecordDiffServiceImpl implements RecordDiffService {

	@Override
	public RecordDiffResult computeDiff(final Record record) {

		// Fist of all we need to check if we has both data
		final byte[] left = record.getLeft();
		if (left == null) {
			throw new MissingDataException("Left data is missing");
		}
		final byte[] right = record.getRight();
		if (right == null) {
			throw new MissingDataException("Right data is missing");
		}

		// Check if sizes are different
		if (left.length != right.length) {
			return new RecordDiffResult(DiffResult.DIFFERENT_LENGTH);
		}

		// Since sizes are equals, we have to check if the data is the same, comparing byte by byte

		// Try to find differences
		final List<DiffPart> diffParts = findDiffParts(left, right);

		// If no difference was found, the data are the same
		if (diffParts.isEmpty()) {
			return new RecordDiffResult(DiffResult.EQUAL);
		}

		// Since there are differences, we return that
		return new RecordDiffResult(DiffResult.NOT_EQUAL, diffParts);
	}

	private List<DiffPart> findDiffParts(final byte[] left, final byte[] right) {
		final List<DiffPart> result = new ArrayList<>();

		int offset = -1;
		int lenght = 1;
		for (int i = 0; i < left.length; i++) {

			// Check if bytes are equals
			if (left[i] == right[i]) {

				// Since they are equals, we have to check if we were counting
				if (offset < 0) {

					// If not just go to next byte
					continue;
				}

				// Since we were counting, we found the end the end of the difference
				result.add(new DiffPart(offset, lenght));

				// reset counters
				offset = -1;
				lenght = 1;
				continue;
			}

			// Since bytes were not equals, we have to check if we were already counting
			if (offset < 0) {
				// If not we set offset and go to next byte
				offset = i;
				continue;
			}

			// Actualy we were already counting, so just increment counting
			lenght++;
		}

		// We reach the end of array, but if we were count it means the last part is different
		if (offset > 0) {
			result.add(new DiffPart(offset, lenght));
		}

		return result;
	}

}
