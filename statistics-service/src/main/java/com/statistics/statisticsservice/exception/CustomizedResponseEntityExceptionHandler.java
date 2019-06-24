package com.statistics.statisticsservice.exception;

import com.statistics.statisticsservice.data.exception.EntityNotFoundException;
import com.statistics.statisticsservice.data.exception.ValidDateNotProvidedException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler  extends ResponseEntityExceptionHandler {
	
	
	// 201 : Everything Went Well and the new entry for stock was updated
	// 204 : Outdated Stocks because newer stock was processed first
	// 400 : Update Failed Due to erronous JSON
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(Instant.now().toString(), ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
		List<ObjectError> globalErrors = ex.getBindingResult().getGlobalErrors();
		StringBuffer buffer = new StringBuffer();
		String error;
		for (FieldError fieldError : fieldErrors) {
			error = fieldError.getField() + ", " + fieldError.getDefaultMessage();
			buffer.append(error);
			buffer.append('-');
		}
		for (ObjectError objectError : globalErrors) {
			error = objectError.getObjectName() + ", " + objectError.getDefaultMessage();
			buffer.append(error);
		}
		ExceptionResponse exceptionResponse = new ExceptionResponse(Instant.now().toString(),buffer.toString(), request.getDescription(false));
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(Instant.now().toString(), ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	public final ResponseEntity<ExceptionResponse> handleUserNotFoundException(EntityNotFoundException ex,
																			   WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(Instant.now().toString(), ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ValidDateNotProvidedException.class)
	public final ResponseEntity<ExceptionResponse> handleUserNotFoundException(ValidDateNotProvidedException ex,
			WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(Instant.now().toString(), ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
	}


}
