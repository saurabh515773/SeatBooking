package com.persistent.deskManagement.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.persistent.deskManagement.dao.FloorPlanRepository;
import com.persistent.deskManagement.entity.FloorPlan;
import com.persistent.deskManagement.model.EnumHelper.CityEnum;
import com.persistent.deskManagement.service.FloorPlanService;

@Service
public class FloorPlanServiceImpl implements FloorPlanService{

	@Autowired
	FloorPlanRepository floorPlanRepository;

	@Override
	public List<FloorPlan> insertAll(List<FloorPlan> floorPlanList) {
		return floorPlanRepository.saveAll(floorPlanList);
	}

	@Override
	public Optional<FloorPlan> findById(String floorId) {
		if(floorPlanRepository.findById(floorId).isPresent()) {
			return floorPlanRepository.findById(floorId);
		}else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<List<FloorPlan>> FindFloorPlanByCity(CityEnum city) {
		if(floorPlanRepository.FindFloorPlanByCity(city).isPresent() && 
				!floorPlanRepository.FindFloorPlanByCity(city).get().isEmpty()) {
			return floorPlanRepository.FindFloorPlanByCity(city);
		}else {
			return Optional.empty();
		}

	}

	@Override
	public Optional<List<String>> findAllBuildingsByCity(CityEnum city) {
		List<String> allBuildings = new ArrayList<String>();

		Optional<List<FloorPlan>> floorPlanList = floorPlanRepository.findAllBuildingsByCity(city);
		if(floorPlanList.isPresent()) {
			for (FloorPlan floorPlan : floorPlanList.get()) {
				allBuildings.add(floorPlan.getBuilding());
			}
		}
		if(allBuildings.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(allBuildings);
	}

	@Override
	public Optional<List<Integer>> findAllFloorByCityAndBuilding(CityEnum city, String building) {
		List<Integer> allFloors = new ArrayList<Integer>();

		Optional<List<FloorPlan>> floorPlanList = floorPlanRepository.findAllFloorByCityAndBuilding(city, building);
		if(floorPlanList.isPresent()) {
			for (FloorPlan floorPlan : floorPlanList.get()) {
				allFloors.add(floorPlan.getFloorNumber());
			}
		}
		if(allFloors.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(allFloors);
	}
}
