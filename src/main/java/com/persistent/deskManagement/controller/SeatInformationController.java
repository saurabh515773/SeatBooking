package com.persistent.deskManagement.controller;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.persistent.deskManagement.entity.Employee;
import com.persistent.deskManagement.entity.SeatBooking;
import com.persistent.deskManagement.entity.SeatInformation;
import com.persistent.deskManagement.exception.ApiRequestException;
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
		}catch (ApiRequestException e) {
			throw new ApiRequestException(e.getMessage());
		}
		
	}
	
	@PostMapping(value = "/booking")
	public ResponseEntity<ResponseObject> seatBooking(
			@RequestParam(name = "employeeId", required = true) Integer employeeId,
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
			
			if(from.isBefore(LocalDateTime.now()) || to.isBefore(LocalDateTime.now())) {
				throw new ApiRequestException("FROM TIME AND TO TIME SHOULD BE GREATER THAN TO CURRENT TIME!!");
			}
			if(from.isAfter(to)) {
				throw new ApiRequestException("FROM TIME SHOULD BE LESS THAN TO TIME!!");
			}
			if(duration.toMinutes()<minTimeInMinutes) {
				throw new ApiRequestException("BOOKING DURATION CANNOT BE LESS THAN "+minTimeInMinutes/60+" HOURS!!");
			}
			if(duration.toMinutes()>maxTimeInMinutes) {
				throw new ApiRequestException("BOOKING DURATION CANNOT BE MORE THAN "+maxTimeInMinutes/60+" HOURS!!");
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
		}catch (ApiRequestException e) {
			throw new ApiRequestException(e.getMessage());
		}
		
	}
	
	@PutMapping(value = "/occupy")
	public ResponseEntity<ResponseObject> seatOccupied(
			@RequestParam(name = "bookingId", required = true) String bookingId) throws Exception {
		
		LOGGER.info("Web Service called : /occupy");
		
		ResponseObject response = new ResponseObject();
		response.setRequestType(CRUDEnum.UPDATE);
		
		try {
			Optional<SeatBooking> seatBookingOpt = seatBookingService.occupySeat(bookingId);
			if(seatBookingOpt.isPresent()) {
				response.setSuccess(true);
				response.setStatus(200);
				response.setStatusText(ResponseStatusEnum.SUCCESS.name());
				response.setObject(seatBookingOpt.get());
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
			}else {
				response.setSuccess(true);
				response.setStatus(204);
				response.setStatusText(ResponseStatusEnum.SUCCESS.name());
				response.setStatusText("BOOKING NOT FOUND!!");
				response.setObject(new String());
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
			}
		}catch (ApiRequestException e) {
			throw new ApiRequestException(e.getMessage());
		}
		
	}
	
	@PutMapping(value = "/return")
	public ResponseEntity<ResponseObject> seatReturn(
			@RequestParam(name = "bookingId", required = true) String bookingId) throws Exception {
		
		LOGGER.info("Web Service called : /return");
		
		ResponseObject response = new ResponseObject();
		response.setRequestType(CRUDEnum.UPDATE);
		
		try {
			Optional<SeatBooking> seatBookingOpt = seatBookingService.returnSeat(bookingId);
			if(seatBookingOpt.isPresent()) {
				response.setSuccess(true);
				response.setStatus(200);
				response.setStatusText(ResponseStatusEnum.SUCCESS.name());
				response.setObject(seatBookingOpt.get());
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
			}else {
				response.setSuccess(true);
				response.setStatus(204);
				response.setStatusText(ResponseStatusEnum.SUCCESS.name());
				response.setStatusText("BOOKING NOT FOUND!!");
				response.setObject(new String());
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
			}
		}catch (ApiRequestException e) {
			throw new ApiRequestException(e.getMessage());
		}
		
	}

	@GetMapping(value = "/allbookedSeat")
	public ResponseEntity<ResponseObject> getBookedSeats(
			@RequestParam(name = "fromTime", required = true) String fromTime,
			@RequestParam(name = "toTime", required = true) String toTime) throws Exception {
		LOGGER.info("Web Service called : /allbookedSeat");

		ResponseObject response = new ResponseObject();
		response.setRequestType(CRUDEnum.LIST);
		
		try {
			LocalDateTime from = LocalDateTime.parse(fromTime);
			LocalDateTime to = LocalDateTime.parse(toTime);
			
			ArrayList<String> allBookedSeats = seatBookingService.allBookedSeats(from, to);

			if(!allBookedSeats.isEmpty()) {
				response.setSuccess(true);
				response.setStatus(200);
				response.setStatusText(ResponseStatusEnum.SUCCESS.name());
				response.setObject(allBookedSeats);
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
			}else {
				response.setSuccess(true);
				response.setStatus(204);
				response.setStatusText(ResponseStatusEnum.SUCCESS.name());
				response.setStatusText("NO SEAT BOOKED FOUND!!");
				response.setObject("NO SEAT BOOKED FOUND!!");
				return new ResponseEntity<ResponseObject>(response, HttpStatus.NO_CONTENT);
			}
		}catch (ApiRequestException e) {
			throw new ApiRequestException(e.getMessage());
		}
		
	}
}
