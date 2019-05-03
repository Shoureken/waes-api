package com.dematte.waestest.controller;

import java.beans.PropertyEditorSupport;
import lombok.RequiredArgsConstructor;

import com.dematte.waestest.model.Side;
import com.dematte.waestest.model.dto.ErrorDTO;
import com.dematte.waestest.model.dto.RecordDiffResult;
import com.dematte.waestest.model.entities.Record;
import com.dematte.waestest.services.RecordDiffService;
import com.dematte.waestest.services.RecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * REST Controller that handle Diff endpoints
 *
 * @author Rodolfo Chiuff Dematte
 * @since 2019-05-01
 */
@Api(value = "V1 Diff Controller")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "v1/diff", produces = APPLICATION_JSON_UTF8_VALUE)
public class V1DiffController {

	private final RecordService recordService;
	private final RecordDiffService recordDiffService;

	/**
	 * Method handle GET request to consult Diff result based on the "id" provided
	 *
	 * @param id Path parameter containing the record identification to consult
	 * @return RecordDiffResult representation of the result
	 */
	@ApiResponses({
			              @ApiResponse(code = 200, message = "OK", response = RecordDiffResult.class),
			              @ApiResponse(code = 404, message = "Not Found", response = ErrorDTO.class),
			              @ApiResponse(code = 400, message = "Missing Data", response = ErrorDTO.class)
	              })
	@GetMapping("{id}")
	@ResponseStatus(HttpStatus.OK)
	public RecordDiffResult consult(
			@ApiParam("Record identification to consult") @PathVariable("id") final String id) {
		final Record record = recordService.get(id);
		return recordDiffService.computeDiff(record);
	}

	/**
	 * Method handle POST request to save the data to be diff-ed
	 *
	 * @param id    Path parameter containing the record identification to save
	 * @param side  Each side data has to be saved, may be "left" or "right"
	 * @param value Binary data to be stored
	 */
	@ApiResponses({
			              @ApiResponse(code = 200, message = "Data stored", response = Void.class),
			              @ApiResponse(code = 400, message = "Invalid Data", response = ErrorDTO.class)
	              })
	@PostMapping("{id}/{side}")
	@ResponseStatus(HttpStatus.OK)
	public void saveData(
			@ApiParam("Record identification") @PathVariable("id") final String id,
			@ApiParam("Each side data has to be saved") @PathVariable("side") final Side side,
			@ApiParam("JSON Base64 encoded data") @RequestBody final byte[] value) {
		recordService.save(id, side, value);
	}

	/**
	 * Registering PropertyEditor to handle Side enum type
	 */
	@InitBinder
	private void initBinder(final WebDataBinder webdataBinder) {
		webdataBinder.registerCustomEditor(Side.class, new SidePropertyEditorSupport());
	}

	/**
	 * PropertyEditor that converts String into Side enum
	 */
	private class SidePropertyEditorSupport extends PropertyEditorSupport {

		@Override
		public void setAsText(final String text) throws IllegalArgumentException {
			final String toUpperCase = text.toUpperCase();
			final Side side = Side.valueOf(toUpperCase);
			setValue(side);
		}

	}

}
