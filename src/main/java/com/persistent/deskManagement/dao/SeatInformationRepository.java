package com.persistent.deskManagement.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.persistent.deskManagement.entity.SeatInformation;
import com.persistent.deskManagement.model.EnumHelper.CityEnum;

@Repository
public interface SeatInformationRepository extends JpaRepository<SeatInformation, Integer> {

	@Query("SELECT s FROM SeatInformation s WHERE UPPER(s.id) = ?1")
	SeatInformation getSeatInformationById(String seatNumber);

	@Query("SELECT s FROM SeatInformation s WHERE s.floorDetail.city = ?1 AND s.floorDetail.building = ?2 AND s.floorDetail.floorNumber = ?3")
	Optional<List<SeatInformation>> findAllSeatsByCityAndBuildingAndFloor(CityEnum cityName, String buildingName,
			Integer floorNumber);

	//Boolean existsById(String seatNumber);

}