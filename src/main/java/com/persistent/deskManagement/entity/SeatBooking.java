package com.persistent.deskManagement.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.persistent.deskManagement.model.EnumHelper.StatusEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatBooking implements Serializable{

	/**
	 * 
	 */
	@JsonIgnore
	private static final long serialVersionUID = 4073748523161327425L;
	
	@Id
	private String bookingId;
	
	private String seatNumber;//for
	
	private Integer employeeId;//for key
	
	private LocalDateTime bookedFrom;
	
	private LocalDateTime bookedTo;
	
	private StatusEnum status;
	
	private Boolean isTimeElapsed;

}
