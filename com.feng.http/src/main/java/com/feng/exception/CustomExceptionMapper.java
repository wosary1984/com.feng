package com.feng.exception;

import java.util.Arrays;
import java.util.List;

import com.feng.exception.def.BadRequestException;
import com.feng.exception.def.NotFoundException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
public class CustomExceptionMapper extends ResponseEntityExceptionHandler {

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	@ExceptionHandler(value = { BadRequestException.class })
	public ResponseEntity<Object> handleNullException(BadRequestException ex) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	/**
	 * Simple structure for sending errors as JSON
	 */
	public class ApiError {
		private HttpStatus status;
		private String message;
		private List<String> errors;

		public ApiError(HttpStatus status, String message, String... errors) {
			this.status = status;
			this.message = message;
			this.errors = Arrays.asList(errors);
		}

		public HttpStatus getStatus() {
			return status;
		}

		public String getMessage() {
			return message;
		}

		public List<String> getErrors() {
			return errors;
		}
	}
}
