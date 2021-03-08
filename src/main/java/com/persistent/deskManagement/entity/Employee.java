package com.persistent.deskManagement.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.persistent.deskManagement.model.EnumHelper.CityEnum;
import com.persistent.deskManagement.model.EnumHelper.GenderEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee implements Serializable{

	/**
	 * 
	 */
	@JsonIgnore
	private static final long serialVersionUID = -1826824075913877083L;
	
	@Id
	private Integer id;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "email_id", unique=true)
	private String emailId;
	
	@Column(name = "mobile_number", unique=true)
	private Long mobileNumber;
	
	@Column(name = "gender")
	private GenderEnum gender;
	
	@Column(nullable = false, name = "is_active")
	private boolean isActive;
	
	@Column(name = "username", unique=true)
	private String username;
	
	@Column(name = "password")
	private String password;	
	
	@Column(name = "isUserLoggedIn")
	private Boolean isUserLoggedIn;	
	
	private CityEnum baseLocation;

}
