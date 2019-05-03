package com.dematte.waestest.controller;

import com.dematte.waestest.exception.BadRequestException;
import com.dematte.waestest.exception.NotFoundException;
import com.dematte.waestest.model.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * ExceptionHandler methods to be shared across Controllers.
 *
 * @author Rodolfo Chiuff Dematte
 * @since 2019-05-01
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * Handle any Exception that extends from NotFoundException returning a HTTP status code NOT_FOUND (404)
	 *
	 * @param e Exception to be handled
	 * @return {@link ErrorDTO} containing the error message
	 */
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErrorDTO> handleNotFoundException(NotFoundException e) {
		final ErrorDTO error = new ErrorDTO(e.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	/**
	 * Handle any Exception that extends from BadRequestException returning a HTTP status code BAD_REQUEST (400)
	 *
	 * @param e Exception to be handled
	 * @return {@link ErrorDTO} containing the error message
	 */
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ErrorDTO> handleBadRequestException(BadRequestException e) {
		final ErrorDTO error = new ErrorDTO(e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	/**
	 * Handle any unexpected Exception returning a HTTP status code INTERNAL_SERVER_ERROR (500)
	 *
	 * @param e Exception to be handled
	 * @return {@link ErrorDTO} containing the error message
	 */
	@ExceptionHandler(Throwable.class)
	public ResponseEntity<ErrorDTO> handleThrowable(Throwable e) {
		final ErrorDTO error = new ErrorDTO(e.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}

}