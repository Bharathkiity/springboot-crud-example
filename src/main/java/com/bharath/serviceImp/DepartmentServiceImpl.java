package com.bharath.serviceImp;

import com.bharath.entites.Department;
import com.bharath.repo.DepartmentRepository;
import com.bharath.service.DepartmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    // CREATE
    @Override
    public Department saveDepartment(Department dept) {
        return departmentRepository.save(dept);
    }

    // READ ALL
    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    // READ BY ID
    @Override
    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id).orElse(null);
    }

    // FIND BY NAME
    @Override
    public Optional<Department> getByDeptName(String deptName) {
        return departmentRepository.findByDeptName(deptName);
    }

    // DELETE
    @Override
    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }
}