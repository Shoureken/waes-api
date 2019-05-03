package com.dematte.waestest.exception;

/**
 * Exception that occurs when data is missing
 *
 * @author Rodolfo Chiuff Dematte
 * @since 2019-05-01
 */
public class MissingDataException extends BadRequestException {

	public MissingDataException(final String message) {
		super(message);
	}

}
