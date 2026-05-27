package com.bharath.dto;

import lombok.Data;

@Data
public class EmployeeDTO {

    private Long id;
    private String name;
    private double salary;

    // Only needed field (not full object)
    private String departmentName;
}