package com.persistent.deskManagement.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.persistent.deskManagement.model.EnumHelper.PreferencesEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatInformation implements Serializable{

	/**
	 * 
	 */
	@JsonIgnore
	private static final long serialVersionUID = 1030928519857247805L;
	
	@Id
	private String seatNumber;
	
	private String cubicleNumber;
	
	private String seatPosition;
	
	private PreferencesEnum preference;
	
	private Integer totalTimeBooked;
	
	private Integer totalTimeOccupied;
	
	private Boolean isAvailable;
	
	private LocalDate lastBookedDate;
	
	private Double avgRating;
	
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_floor_id"))
	private FloorPlan floorDetail;

}
