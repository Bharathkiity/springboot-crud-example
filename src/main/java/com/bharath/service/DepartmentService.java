package com.bharath.service;

import com.bharath.entites.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {

    Department saveDepartment(Department dept);

    List<Department> getAllDepartments();

    Department getDepartmentById(Long id);

    Optional<Department> getByDeptName(String deptName);

    void deleteDepartment(Long id);
}