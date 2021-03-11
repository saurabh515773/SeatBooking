package com.persistent.deskManagement.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ApiRequestException extends RuntimeException {

	/**
	 * 
	 */
	@JsonIgnore
	private static final long serialVersionUID = 5123694717868232267L;

	public ApiRequestException(String message) {
		super(message);
	}
	
}