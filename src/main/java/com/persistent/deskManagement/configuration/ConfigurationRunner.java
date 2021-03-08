package com.persistent.deskManagement.configuration;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Scope;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.persistent.deskManagement.entity.Employee;
import com.persistent.deskManagement.service.EmployeeService;
import com.persistent.deskManagement.service.LoaderService;

/**
 * This Runner is invoked everytime application starts. Intend of this runner is
 * to check system level configuration records (generally only one record) are
 * present If it does not find system level records then it will create one with
 * default values
 * 
 */
@Order(Ordered.LOWEST_PRECEDENCE)
@Scope(value = "singleton")
@Component("configurationRunner")
public class ConfigurationRunner implements ApplicationRunner {

	private static final Logger logger = LogManager.getLogger(ConfigurationRunner.class);

	@Autowired
	LoaderService loaderService;
	
	@Autowired
	EmployeeService employeeService;
	
	// TODO first time initialization/loading of data
	@Override
	public void run(ApplicationArguments args) throws Exception {	
		
		List<Employee> allEmp = employeeService.getAllEmployee();
		if(allEmp!=null && allEmp.isEmpty()) {
			if(loaderService.loadAllEmployees()) {
				if(loaderService.loadAllFloor()) {
					if(loaderService.loadAllSeat()) {
						System.out.println("Data loaded successfully");
					}
				}
			}
		}
	}
}