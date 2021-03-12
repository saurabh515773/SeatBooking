package com.persistent.deskManagement.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.persistent.deskManagement.entity.Employee;
import com.persistent.deskManagement.exception.ApiRequestException;
import com.persistent.deskManagement.exception.EmployeeNotFoundException;
import com.persistent.deskManagement.model.EnumHelper.CRUDEnum;
import com.persistent.deskManagement.model.EnumHelper.ResponseStatusEnum;
import com.persistent.deskManagement.model.ResponseObject;
import com.persistent.deskManagement.service.EmployeeService;

@RestController
@CrossOrigin("*")
public class AuthenticationController {

	@Autowired
	EmployeeService employeeService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

	@GetMapping("/welcome")
	@ResponseBody
	public ResponseEntity<String> welcome() {
		String response = "Welcome to Desk Management Application ";
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<ResponseObject> userValidateAndLogin(
			@RequestParam(name = "employeeId", required = true) Integer employeeId) throws Exception {

		LOGGER.info("Web Service called : /login");
		
		ResponseObject response = new ResponseObject();
		response.setRequestType(CRUDEnum.QUERY);
		
		Optional<Employee> empObj = employeeService.getEmployeeById(employeeId);
		try {
			if(empObj.isPresent()) {
				response.setSuccess(true);
				response.setStatus(200);
				response.setStatusText(ResponseStatusEnum.SUCCESS.name());
				response.setObject(empObj.get());
				return new ResponseEntity<ResponseObject>(response, HttpStatus.OK);
			}else {
				throw new EmployeeNotFoundException(employeeId);
			}
		}catch (ApiRequestException e) {
			throw new ApiRequestException(e.getMessage());
		}
	}
}
