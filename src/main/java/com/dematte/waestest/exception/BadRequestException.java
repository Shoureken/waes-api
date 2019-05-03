package com.dematte.waestest.exception;

/**
 * Exception for HTTP response status BAD_REQUEST (400)
 *
 * @author Rodolfo Chiuff Dematte
 * @since 2019-05-01
 */
public class BadRequestException extends RuntimeException {

	public BadRequestException(final String message) {
		super(message);
	}

	public BadRequestException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
