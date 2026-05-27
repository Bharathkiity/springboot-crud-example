package com.bharath.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bharath.dto.EmployeeDTO;
import com.bharath.entites.Employee;
import com.bharath.service.EmployeeService;

@RestController // Marks this class as REST API controller
@RequestMapping("/employees") // Base URL
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

 // ==========================
    // CREATE
    // ==========================
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee emp) {

        Employee saved = employeeService.saveEmployee(emp);

        // 201 CREATED
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // ==========================
    // READ ALL
    // ==========================
//    @GetMapping
//    public ResponseEntity<List<Employee>> getAllEmployees() {
//
//        return ResponseEntity.ok(employeeService.getAllEmployees());
//    }
    
    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    // ==========================
    // READ BY ID
    // ==========================
//    @GetMapping("/{id}")
//    public ResponseEntity<Employee> getEmployee(@PathVariable Long id) {
//
//        Employee emp = employeeService.getEmployeeById(id);
//
//        if (emp != null) {
//            return ResponseEntity.ok(emp);
//        }
//
//        // 404 NOT FOUND
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//    }
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable Long id) {

        EmployeeDTO dto = employeeService.getEmployeeById(id);

        if (dto != null) {
            return ResponseEntity.ok(dto);
        }

        return ResponseEntity.notFound().build();
    }
    // ==========================
    // UPDATE
    // ==========================
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id,
                                                   @RequestBody Employee emp) {

        Employee updated = employeeService.updateEmployee(id, emp);

        if (updated != null) {
            return ResponseEntity.ok(updated);
        }

        return ResponseEntity.notFound().build();
    }

    // ==========================
    // DELETE
    // ==========================
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {

        employeeService.deleteEmployee(id);

        return ResponseEntity.ok("Employee Deleted Successfully");
    }
    // ==========================
    // QUERY PARAM
    // ==========================
    // GET /employees/search?name=John
    @GetMapping("/search")
    public List<Employee> getByName(@RequestParam String name) {
        return employeeService.getEmployeesByName(name);
    }

    // ==========================
    // JAVA 8 STREAM FILTER
    // ==========================
    // GET /employees/high-salary?amount=50000
    @GetMapping("/high-salary")
    public List<Employee> getHighSalary(@RequestParam double amount) {
        return employeeService.getHighSalaryEmployees(amount);
    }
}