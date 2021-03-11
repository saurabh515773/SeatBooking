package com.persistent.deskManagement.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class EmployeeNotFoundException extends RuntimeException{
	/**
	 * 
	 */
	@JsonIgnore
	private static final long serialVersionUID = 1785759506181336860L;

	public EmployeeNotFoundException(Integer id) {
		
		super("EMPLOYEE NOT FOUND WITH ID : "+ id);
	}
}
