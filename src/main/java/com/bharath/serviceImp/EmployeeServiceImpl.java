package com.bharath.serviceImp;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bharath.dto.EmployeeDTO;
import com.bharath.entites.Employee;
import com.bharath.exception.EmployeeNotFoundException;
import com.bharath.repo.EmployeeRepository;
import com.bharath.service.EmployeeService;

import jakarta.transaction.Transactional;

@Service // Marks this class as Service layer (Spring Bean)
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository; // Inject Repository
    
    @Autowired
    private ModelMapper modelMapper;

    // ==========================
    // CREATE
    // ==========================
    @Override
    public Employee saveEmployee(Employee emp) {
        // Save employee into DB
        return employeeRepository.save(emp);
    }

    // ==========================
    // READ ALL
    // ==========================
//    @Override
//    public List<Employee> getAllEmployees() {
//        // Fetch all employees from DB
//        return employeeRepository.findAll();
//    }

    // ==========================
    // READ BY ID
    // ==========================
//    @Override
//    public Employee getEmployeeById(Long id) {
//        // findById returns Optional → handle null safely
//        return employeeRepository.findById(id).orElse(null);
//    }
    @Transactional
    @Override
    public List<EmployeeDTO> getAllEmployees() {

        return employeeRepository.findAll()
                .stream()
                .map(emp -> {
                    EmployeeDTO dto = modelMapper.map(emp, EmployeeDTO.class);

                    if (emp.getDepartment() != null) {
                        dto.setDepartmentName(emp.getDepartment().getDeptName());
                    }

                    return dto;
                })
                .toList(); // Java 16+ cleaner
    }
    @Transactional
    @Override
    public EmployeeDTO getEmployeeById(Long id) {

        // throw exception if not found
        Employee emp = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id " + id));

        // convert to DTO
        EmployeeDTO dto = modelMapper.map(emp, EmployeeDTO.class);

        if (emp.getDepartment() != null) {
            dto.setDepartmentName(emp.getDepartment().getDeptName());
        }

        return dto;
    }
    // ==========================
    // UPDATE
    // ==========================
//    @Override
//    public Employee updateEmployee(Long id, Employee emp) {
//
//        // First get existing employee
//        Employee existing = employeeRepository.findById(id).orElse(null);
//
//        if (existing != null) {
//            // Update values
//            existing.setName(emp.getName());
//            existing.setSalary(emp.getSalary());
//            existing.setAddress(emp.getAddress());
//            existing.setDepartment(emp.getDepartment());
//            existing.setTasks(emp.getTasks());
//            existing.setProjects(emp.getProjects());
//
//            // Save updated employee
//            return employeeRepository.save(existing);
//        }
//
//        return null; // if not found
//    }
    @Transactional
    @Override
    public Employee updateEmployee(Long id, Employee emp) {

        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id " + id));

        existing.setName(emp.getName());
        existing.setSalary(emp.getSalary());
        existing.setAddress(emp.getAddress());
        existing.setDepartment(emp.getDepartment());
        existing.setTasks(emp.getTasks());
        existing.setProjects(emp.getProjects());

        return employeeRepository.save(existing);
    }
    // ==========================
    // DELETE
    // ==========================
//    @Override
//    public void deleteEmployee(Long id) {
//        // Delete employee by id
//        employeeRepository.deleteById(id);
//    }
    @Override
    public void deleteEmployee(Long id) {

        if (!employeeRepository.existsById(id)) {
            throw new EmployeeNotFoundException("Employee not found with id " + id);
        }

        employeeRepository.deleteById(id);
    }

    // ==========================
    // CUSTOM QUERY (BY NAME)
    // ==========================
    @Override
    public List<Employee> getEmployeesByName(String name) {
        // Calls custom method from repository
        return employeeRepository.findByName(name);
    }

    // ==========================
    // JAVA 8 STREAM 🔥
    // ==========================
    @Override
    public List<Employee> getHighSalaryEmployees(double salary) {

        // Get all employees → convert to stream → filter → collect
        return employeeRepository.findAll()
                .stream()
                .filter(emp -> emp.getSalary() > salary)
                .collect(Collectors.toList());
    }
}