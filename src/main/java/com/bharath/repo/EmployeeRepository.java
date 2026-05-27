 package com.bharath.repo;

import com.bharath.entites.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Find employees by name
    List<Employee> findByName(String name);

    // Find employees with salary greater than
    List<Employee> findBySalaryGreaterThan(double salary);

}