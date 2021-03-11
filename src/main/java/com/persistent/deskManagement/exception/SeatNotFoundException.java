package com.persistent.deskManagement.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class SeatNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	@JsonIgnore
	private static final long serialVersionUID = 1499271107140334854L;

	public SeatNotFoundException(Integer id) {
		
		super("Seat Not Found : "+ id);
	}

}
