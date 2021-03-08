package com.persistent.deskManagement.service;

import java.util.List;
import java.util.Optional;

import com.persistent.deskManagement.entity.SeatInformation;
import com.persistent.deskManagement.model.EnumHelper.CityEnum;

public interface SeatInformationService {

	public SeatInformation findAndReplaceSeatInformation(SeatInformation seatInformation);
	
	public List<SeatInformation> insertAll(List<SeatInformation> seatInformationList);

	public Optional<List<SeatInformation>> findAllSeatsByCityAndBuildingAndFloor(CityEnum cityName, String buildingName,
			Integer floorNumber);
}
