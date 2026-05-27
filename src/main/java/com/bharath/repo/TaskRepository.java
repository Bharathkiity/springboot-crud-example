package com.bharath.repo;

import com.bharath.entites.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    // Get tasks by employee id
    List<Task> findByEmployeeId(Long employeeId);
}