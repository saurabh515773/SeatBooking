package com.persistent.deskManagement.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.persistent.deskManagement.entity.FloorPlan;
import com.persistent.deskManagement.model.EnumHelper.CityEnum;

@Repository
public interface FloorPlanRepository extends JpaRepository<FloorPlan, String> {

	@Query("select s from FloorPlan s where s.city = ?1")
	Optional<List<FloorPlan>> FindFloorPlanByCity(CityEnum city);

	@Query("select DISTINCT s from FloorPlan s where s.city = ?1")
	Optional<List<FloorPlan>> findAllBuildingsByCity(CityEnum city);

	@Query("select DISTINCT s from FloorPlan s where s.city = ?1 AND s.building = ?2")
	Optional<List<FloorPlan>> findAllFloorByCityAndBuilding(CityEnum city, String buildingName);
}