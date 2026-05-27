package com.bharath.entites;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Entity // This class becomes table
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double salary;

    // ===============================
    // 1️⃣ OneToOne → One Employee has one Address
    // ===============================
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id") // FK column
    private Address address;

    // ===============================
    // 2️⃣ ManyToOne → Many Employees belong to one Department
    // ===============================
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    // ===============================
    // 3️⃣ OneToMany → One Employee has many Tasks
    // ===============================
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Task> tasks;

    // ===============================
    // 4️⃣ ManyToMany → Employees work on many Projects
    // ===============================
    @ManyToMany
    @JoinTable(
        name = "employee_project", // join table
        joinColumns = @JoinColumn(name = "employee_id"),
        inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private List<Project> projects=new ArrayList<>();

    // Constructors
    public Employee() {}



    // Getters and Setters
}