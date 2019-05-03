package com.dematte.waestest.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.annotations.ApiModel;

/**
 * Class that represents a peace of difference
 *
 * @author Rodolfo Chiuff Dematte
 * @since 2019-05-01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("DifferencePart")
public class DiffPart {

	private int offset;
	private int lenght;

}
