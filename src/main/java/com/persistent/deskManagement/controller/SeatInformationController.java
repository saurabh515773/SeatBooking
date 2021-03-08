package com.persistent.deskManagement.controller;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.persistent.deskManagement.entity.SeatBooking;
import com.persistent.deskManagement.entity.SeatInformation;
import com.persistent.deskManagement.model.EnumHelper.CRUDEnum;
import com.persistent.deskManagement.model.EnumHelper.CityEnum;
import com.persistent.deskManagement.model.EnumHelper.ResponseStatusEnum;
import com.persistent.deskManagement.model.ResponseObject;
import com.persistent.deskManagement.service.SeatBookingService;
import com.persistent.deskManagement.service.SeatInformationService;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/api/seat")
public class SeatInformationController {

	@Value("${spring.deskmanagement.minTime}")
	private Long minTimeInMinutes;

	@Value("${spring.deskmanagement.maxTime}")
	private Long maxTimeInMinutes;
	
	@Autowired
	SeatInformationService seatInformationService;
	
	@Autowired
	SeatBookingService seatBookingService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);
	
	@GetMapping(value = "/getallseat")
	public ResponseEntity<ResponseObject> getAllSeatsByCityBuildingFloorNumber(
			@RequestParam(name = "cityName", required = true) CityEnum cityName,
			@RequestParam(name = "buildingName", required = true) String buildingName,
			@RequestParam(name = "floorNumber", required = true) Integer floorNumber) throws Exception {
		
		LOGGER.info("Web Service called : /floorNumber");
		
		ResponseObject response = new ResponseObject();
		response.setRequestType(CRUDEnum.QUERY);
		
		try {
			Optional<List<SeatInformation>> seatList = seatInformationService.findAllSeatsByCityAndBuildingAndFloor(cityName,
					buildingName, floorNumber);
			if(seatList.isPresent()) {
				response.setSuccess(true);
				response.setStatus(200);
				response.setStatusText(ResponseStatusEnum.SUCCESS.name());
				response.setObject(seatList.get());
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
			}else {
				response.setSuccess(true);
				response.setStatus(204);
				response.setStatusText(ResponseStatusEnum.SUCCESS.name());
				response.setStatusText("NO FLOOR FOUND!!");
				response.setObject(new String());
				return new ResponseEntity<ResponseObject>(response, HttpStatus.NO_CONTENT);
			}
		}catch (Exception e) {
			response.setObject(new String());
			response.setStatus(500);
			response.setSuccess(false);
			response.setStatusText(ResponseStatusEnum.FAILURE.name().concat(" -> ").concat(e.getMessage()));
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PostMapping(value = "/booking")
	public ResponseEntity<ResponseObject> getAllSeats(
			@RequestParam(name = "employeeId", required = true) String employeeId,
			@RequestParam(name = "seatNumber", required = true) String seatNumber,
			@RequestParam(name = "fromTime", required = true) String fromTime,
			@RequestParam(name = "toTime", required = true) String toTime) throws Exception {
		
		LOGGER.info("Web Service called : /booking");
		
		ResponseObject response = new ResponseObject();
		response.setRequestType(CRUDEnum.CREATE);
		
		try {
			LocalDateTime from = LocalDateTime.parse(fromTime);
			LocalDateTime to = LocalDateTime.parse(toTime);
			
			Duration duration = Duration.between(from, to);
			
			if(duration.toMinutes()<minTimeInMinutes) {
				response.setSuccess(false);
				response.setStatus(404);
				response.setStatusText(ResponseStatusEnum.SUCCESS.name());
				response.setStatusText("BOOKING DURATION CANNOT BE LESS THAN 1 HOURS!!");
				response.setObject(new String());
				return new ResponseEntity<ResponseObject>(response, HttpStatus.BAD_REQUEST);
			}
			if(duration.toMinutes()>maxTimeInMinutes) {
				response.setSuccess(false);
				response.setStatus(404);
				response.setStatusText(ResponseStatusEnum.SUCCESS.name());
				response.setStatusText("BOOKING DURATION CANNOT BE MORE THAN 9 HOURS!!");
				response.setObject(new String());
				return new ResponseEntity<ResponseObject>(response, HttpStatus.BAD_REQUEST);
			}
			
			Boolean isSeatAvailable = seatBookingService.findSeatAvailability(seatNumber,
					from, to);
			if(isSeatAvailable) {
				SeatBooking seatBookingObj = seatBookingService.seatBooking(seatNumber,
						from, to, employeeId);
				response.setSuccess(true);
				response.setStatus(200);
				response.setStatusText(ResponseStatusEnum.SUCCESS.name());
				response.setObject(seatBookingObj);
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
			}else {
				response.setSuccess(true);
				response.setStatus(204);
				response.setStatusText(ResponseStatusEnum.SUCCESS.name());
				response.setStatusText("SEAT ALREADY BOOKED ON SAME TIME SLOT!!");
				response.setObject(new String());
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
			}
		}catch (Exception e) {
			e.printStackTrace();
			response.setObject(new String());
			response.setStatus(500);
			response.setSuccess(false);
			response.setStatusText(ResponseStatusEnum.FAILURE.name().concat(" -> ").concat(e.getMessage()));
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
}
