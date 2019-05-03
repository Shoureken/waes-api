package com.dematte.waestest.model.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;

/**
 * Class that holds the diff result and parts that are different if any
 *
 * @author Rodolfo Chiuff Dematte
 * @since 2019-05-01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel("DifferenceResult")
public class RecordDiffResult {

	private DiffResult result;
	private List<DiffPart> parts;

	public RecordDiffResult(final DiffResult result) {
		this(result, null);
	}

}
