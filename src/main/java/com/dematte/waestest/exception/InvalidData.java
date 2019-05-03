package com.dematte.waestest.exception;

/**
 * Exception for when invalid binary data was provided
 *
 * @author Rodolfo Chiuff Dematte
 * @since 2019-05-01
 */
public class InvalidData extends BadRequestException {

	public InvalidData(final String message, final Throwable cause) {
		super(message, cause);
	}

}
