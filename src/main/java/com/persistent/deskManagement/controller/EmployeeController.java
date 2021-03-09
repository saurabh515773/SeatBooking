package com.persistent.deskManagement.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.persistent.deskManagement.entity.Employee;
import com.persistent.deskManagement.entity.SeatBooking;
import com.persistent.deskManagement.model.EnumHelper.CRUDEnum;
import com.persistent.deskManagement.model.EnumHelper.ResponseStatusEnum;
import com.persistent.deskManagement.model.RequestObject;
import com.persistent.deskManagement.model.ResponseObject;
import com.persistent.deskManagement.service.EmployeeService;
import com.persistent.deskManagement.service.SeatBookingService;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/api/employee")
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	SeatBookingService seatBookingService;

	private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<ResponseObject> createUser(@Valid @RequestBody RequestObject request) throws Exception {
		LOGGER.info("Web Service called : /register");


		return ResponseEntity.ok(new ResponseObject());
	}

	@GetMapping(value = "/inq")
	public ResponseEntity<ResponseObject> retrieveUser(@PathVariable @Min(1) int empId) throws Exception {
		LOGGER.info("Web Service called : /register");

		ResponseObject response = new ResponseObject();
		response.setRequestType(CRUDEnum.QUERY);
		
		try {
			Optional<Employee> employeeObj = employeeService.getEmployeeById(empId);

			if(employeeObj.isPresent()) {
				response.setSuccess(true);
				response.setStatus(200);
				response.setStatusText(ResponseStatusEnum.SUCCESS.name());
				response.setObject(employeeObj);
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
			}else {
				response.setSuccess(true);
				response.setStatus(204);
				response.setStatusText(ResponseStatusEnum.SUCCESS.name());
				response.setStatusText("NO EMPLOYEE FOUND!!");
				response.setObject(employeeObj);
				return new ResponseEntity<ResponseObject>(response, HttpStatus.NO_CONTENT);
			}
		}catch (Exception e) {
			response.setObject(new Employee());
			response.setStatus(500);
			response.setSuccess(false);
			response.setStatusText(ResponseStatusEnum.FAILURE.name().concat(" -> ").concat(e.getMessage()));
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping(value = "/myAllbookings")
	public ResponseEntity<ResponseObject> getAllBookings(
			@RequestParam(name = "employeeId", required = true) Integer employeeId,
			@RequestParam(name = "fromTime", required = true) String fromTime,
			@RequestParam(name = "toTime", required = true) String toTime) throws Exception {
		
		LOGGER.info("Web Service called : /myAllbookings");

		ResponseObject response = new ResponseObject();
		response.setRequestType(CRUDEnum.LIST);
		
		try {
			LocalDateTime from = LocalDateTime.parse(fromTime);
			LocalDateTime to = LocalDateTime.parse(toTime);
			
			Optional<List<SeatBooking>> allBookedSeats = seatBookingService.allEmployeeBookedSeats(employeeId, from, to);

			if(allBookedSeats.isPresent()) {
				response.setSuccess(true);
				response.setStatus(200);
				response.setStatusText(ResponseStatusEnum.SUCCESS.name());
				response.setObject(allBookedSeats.get());
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
			}else {
				response.setSuccess(true);
				response.setStatus(204);
				response.setStatusText(ResponseStatusEnum.SUCCESS.name());
				response.setStatusText("NO SEAT BOOKED FOUND!!");
				response.setObject("NO SEAT BOOKED FOUND!!");
				return new ResponseEntity<ResponseObject>(response, HttpStatus.NO_CONTENT);
			}
		}catch (Exception e) {
			response.setObject(new Employee());
			response.setStatus(500);
			response.setSuccess(false);
			response.setStatusText(ResponseStatusEnum.FAILURE.name().concat(" -> ").concat(e.getMessage()));
			return new ResponseEntity<ResponseObject>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
}
