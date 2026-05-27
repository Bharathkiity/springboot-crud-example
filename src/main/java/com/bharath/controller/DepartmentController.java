package com.bharath.controller;

import com.bharath.entites.Department;
import com.bharath.service.DepartmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    // CREATE
    @PostMapping
    public Department create(@RequestBody Department dept) {
        return departmentService.saveDepartment(dept);
    }

    // READ ALL
    @GetMapping
    public List<Department> getAll() {
        return departmentService.getAllDepartments();
    }

    // READ BY ID
    @GetMapping("/{id}")
    public Department getById(@PathVariable Long id) {
        return departmentService.getDepartmentById(id);
    }

    // SEARCH BY NAME
    // GET /departments/search?name=IT
    @GetMapping("/search")
    public Department getByName(@RequestParam String name) {
        return departmentService.getByDeptName(name).orElse(null);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return "Department Deleted";
    }
}