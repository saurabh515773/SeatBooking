package com.persistent.deskManagement.service;

import java.util.List;
import java.util.Optional;

import com.persistent.deskManagement.entity.Employee;

public interface EmployeeService {

	Optional<Employee> getEmployeeById(int username);
	
	List<Employee> insertAll(List<Employee> empList);
	
	List<Employee> getAllEmployee();
}
