package com.dematte.waestest.exception;

/**
 * Exception for HTTP response status NOT_FOUND (404)
 *
 * @author Rodolfo Chiuff Dematte
 * @since 2019-05-01
 */
public class NotFoundException extends RuntimeException {

	public NotFoundException(final String message) {
		super(message);
	}

}
