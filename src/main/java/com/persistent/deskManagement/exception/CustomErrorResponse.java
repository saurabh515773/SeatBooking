package com.persistent.deskManagement.exception;

import java.time.LocalDateTime;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@XmlRootElement
@Data
public class CustomErrorResponse {

	private LocalDateTime timestamp;
	
	private int status;
	
	private String error;
}
