package com.persistent.deskManagement.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.persistent.deskManagement.model.EnumHelper.CityEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FloorPlan implements Serializable{

	/**
	 * 
	 */
	@JsonIgnore
	private static final long serialVersionUID = -410673680168184916L;
	
	@Id
	private String floorId;
	
	private CityEnum city;
	
	private String building;
	
	private Integer floorNumber;
	
	private Integer noOfCubicle;

}
