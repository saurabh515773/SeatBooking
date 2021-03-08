package com.persistent.deskManagement.service;

import java.util.List;
import java.util.Optional;

import com.persistent.deskManagement.entity.FloorPlan;
import com.persistent.deskManagement.model.EnumHelper.CityEnum;

public interface FloorPlanService {

	List<FloorPlan> insertAll(List<FloorPlan> empList);
	
	public Optional<FloorPlan> findById(String floorId);

	Optional<List<FloorPlan>> FindFloorPlanByCity(CityEnum city);
	
	Optional<List<String>> findAllBuildingsByCity(CityEnum city);
	
	Optional<List<Integer>> findAllFloorByCityAndBuilding(CityEnum city, String building);
	
}
