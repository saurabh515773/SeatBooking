package com.persistent.deskManagement.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.persistent.deskManagement.exception.ApiRequestException;
import com.persistent.deskManagement.model.EnumHelper.CRUDEnum;
import com.persistent.deskManagement.model.EnumHelper.CityEnum;
import com.persistent.deskManagement.model.EnumHelper.ResponseStatusEnum;
import com.persistent.deskManagement.model.ResponseObject;
import com.persistent.deskManagement.service.FloorPlanService;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/api/floor")
public class FloorPlanController {

	@Autowired
	FloorPlanService floorPlanService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);
	
	@GetMapping(value = "/building")
	public ResponseEntity<ResponseObject> getAllbuildings(
			@RequestParam(name = "cityName", required = true) CityEnum cityName
			) throws Exception {
		LOGGER.info("Web Service called : /building");
		
		ResponseObject response = new ResponseObject();
		response.setRequestType(CRUDEnum.QUERY);
		
		try {
			Optional<List<String>> buildingList = floorPlanService.findAllBuildingsByCity(cityName);
			if(buildingList.isPresent()) {
				response.setSuccess(true);
				response.setStatus(200);
				response.setStatusText(ResponseStatusEnum.SUCCESS.name());
				response.setObject(buildingList.get());
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
			}else {
				response.setSuccess(true);
				response.setStatus(204);
				response.setStatusText(ResponseStatusEnum.SUCCESS.name());
				response.setStatusText("NO BUILDING FOUND!!");
				response.setObject(new String());
				return new ResponseEntity<ResponseObject>(response, HttpStatus.NO_CONTENT);
			}
		}catch (ApiRequestException e) {
			throw new ApiRequestException(e.getMessage());
		}
		
	}
	
	@GetMapping(value = "/floorlist")
	public ResponseEntity<ResponseObject> getAllFloors(
			@RequestParam(name = "cityName", required = true) CityEnum cityName,
			@RequestParam(name = "buildingName", required = true) String buildingName) throws Exception {
		
		LOGGER.info("Web Service called : /floorNumber");
		
		ResponseObject response = new ResponseObject();
		response.setRequestType(CRUDEnum.QUERY);
		
		try {
			Optional<List<Integer>> floorList = floorPlanService.findAllFloorByCityAndBuilding(cityName,
					buildingName);
			if(floorList.isPresent()) {
				response.setSuccess(true);
				response.setStatus(200);
				response.setStatusText(ResponseStatusEnum.SUCCESS.name());
				response.setObject(floorList.get());
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
			}else {
				response.setSuccess(true);
				response.setStatus(204);
				response.setStatusText(ResponseStatusEnum.SUCCESS.name());
				response.setStatusText("NO FLOOR FOUND!!");
				response.setObject(new String());
				return new ResponseEntity<ResponseObject>(response, HttpStatus.NO_CONTENT);
			}
		}catch (ApiRequestException e) {
			throw new ApiRequestException(e.getMessage());
		}
		
	}
}
