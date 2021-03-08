package com.persistent.deskManagement.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.persistent.deskManagement.entity.Employee;

@Repository
@Transactional
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	@Query("SELECT s FROM Employee s WHERE UPPER(s.id) = ?1")
	Employee getEmployeeById(int empId);
    
    @Query("select s from Employee s where s.emailId = ?1")
    Employee getEmployeeByEmailId(String emailId);
    
    @Query("select s from Employee s where s.mobileNumber = ?1")
    Employee getEmployeeByMobileNumber(Long mobileNumber);
    
    @Query("select s from Employee s where s.username = ?1")
    Employee getEmployeeByUsername(String username);

}