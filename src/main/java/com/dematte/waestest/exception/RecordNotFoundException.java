package com.dematte.waestest.exception;

/**
 * Exception that occurs when no Record is found
 *
 * @author Rodolfo Chiuff Dematte
 * @since 2019-05-01
 */
public class RecordNotFoundException extends NotFoundException {

	public RecordNotFoundException(final String message) {
		super(message);
	}

}
