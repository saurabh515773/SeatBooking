package com.persistent.deskManagement.model;

import java.io.Serializable;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.persistent.deskManagement.model.EnumHelper.CRUDEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseObject implements Serializable {

	/**
	 * 
	 */
	@JsonIgnore
	private static final long serialVersionUID = 9103868743298825774L;
	
    private CRUDEnum requestType;
    private String token;
    private Boolean success;
    private ArrayList<ErrorData> detailErrors;
    private Object object;
    private int status;
    private String statusText;
}
