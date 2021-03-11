package com.persistent.deskManagement.exception;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler{
	
	//specific exception
	@ExceptionHandler(SeatNotFoundException.class)
	public final ResponseEntity<CustomErrorResponse> handleSeatNotFoundException
		(SeatNotFoundException ex, WebRequest response) throws IOException {
		
		CustomErrorResponse customError = new CustomErrorResponse();
		customError.setTimestamp(LocalDateTime.now());
		customError.setStatus(HttpStatus.NOT_FOUND.value());
		customError.setError(ex.getMessage());
		
		return new ResponseEntity<>(customError, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ApiRequestException.class)
	public final ResponseEntity<CustomErrorResponse> handleApiRequestException
		(ApiRequestException ex, WebRequest response) throws IOException {
		
		CustomErrorResponse customError = new CustomErrorResponse();
		customError.setTimestamp(LocalDateTime.now());
		customError.setStatus(HttpStatus.BAD_REQUEST.value());
		customError.setError(ex.getMessage());
		
		return new ResponseEntity<>(customError, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(EmployeeNotFoundException.class)
	public final ResponseEntity<CustomErrorResponse> handleEmployeeNotFoundException
		(EmployeeNotFoundException ex, WebRequest response) throws IOException {
		
		CustomErrorResponse customError = new CustomErrorResponse();
		customError.setTimestamp(LocalDateTime.now());
		customError.setStatus(HttpStatus.NO_CONTENT.value());
		customError.setError(ex.getMessage());
		
		return new ResponseEntity<>(customError, HttpStatus.NO_CONTENT);
	}
//	
	//global exception	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<CustomErrorResponse> handleException
		(Exception ex, WebRequest response) throws IOException {
		
		CustomErrorResponse customError = new CustomErrorResponse();
		customError.setTimestamp(LocalDateTime.now());
		customError.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		customError.setError("FAILURE -> "+ex.getMessage());
		
		return new ResponseEntity<>(customError, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
