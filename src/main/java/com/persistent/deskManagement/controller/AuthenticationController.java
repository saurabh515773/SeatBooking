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
	public String welcome() {
		return "Welcome to Desk Management Application";
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
				response.setSuccess(true);
				response.setStatus(204);
				response.setStatusText(ResponseStatusEnum.SUCCESS.name());
				response.setStatusText("EMPLOYEE NOT FOUND!!");
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
}
