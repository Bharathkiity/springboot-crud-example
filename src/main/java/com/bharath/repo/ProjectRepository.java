package com.bharath.repo;

import com.bharath.entites.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    // Find project by name
    List<Project> findByProjectName(String projectName);
}