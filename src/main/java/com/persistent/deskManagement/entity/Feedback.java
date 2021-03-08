package com.persistent.deskManagement.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Feedback implements Serializable{

	/**
	 * 
	 */
	@JsonIgnore
	private static final long serialVersionUID = -5813014785736186819L;

	@Id
	private String feedbackId;

	private String seatNumber;

	private String employeeId;

	private String comment;

	private Double rating;

	private LocalDateTime bookedDateTime;
}
