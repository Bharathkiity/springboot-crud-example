package com.bharath.service;

import com.bharath.entites.Project;

import java.util.List;

public interface ProjectService {

    Project saveProject(Project project);

    List<Project> getAllProjects();

    Project getProjectById(Long id);

    List<Project> getProjectsByName(String name);

    void deleteProject(Long id);
}