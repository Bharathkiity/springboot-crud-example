package com.bharath.service;

import java.util.List;

import com.bharath.dto.EmployeeDTO;
import com.bharath.entites.Employee;

public interface EmployeeService {

    // CREATE
    Employee saveEmployee(Employee emp);

    // READ
//    List<Employee> getAllEmployees();
//
//    Employee getEmployeeById(Long id);
    
		
	List<EmployeeDTO> getAllEmployees();
	
	
	EmployeeDTO getEmployeeById(Long id);
	

    // UPDATE
    Employee updateEmployee(Long id, Employee emp);

    // DELETE
    void deleteEmployee(Long id);

    // CUSTOM
    List<Employee> getEmployeesByName(String name);

    List<Employee> getHighSalaryEmployees(double salary);
    
   
}