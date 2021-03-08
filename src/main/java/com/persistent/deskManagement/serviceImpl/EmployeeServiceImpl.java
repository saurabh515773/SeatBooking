package com.persistent.deskManagement.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.persistent.deskManagement.dao.EmployeeRepository;
import com.persistent.deskManagement.entity.Employee;
import com.persistent.deskManagement.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService{

	@Autowired
	EmployeeRepository employeeRepo;
	
	@Override
	public Optional<Employee> getEmployeeById(int empId) {
		Employee employeeObj = employeeRepo.getEmployeeById(empId);
		if(employeeObj!=null) {
			return Optional.of(employeeObj);
		}else {
			return Optional.empty();
		}
	}

	@Override
	public List<Employee> insertAll(List<Employee> empList) {
		return employeeRepo.saveAll(empList);
	}

	@Override
	public List<Employee> getAllEmployee() {
		return employeeRepo.findAll();
	}

}
