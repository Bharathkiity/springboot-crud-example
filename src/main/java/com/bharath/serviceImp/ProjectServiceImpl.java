package com.bharath.serviceImp;

import com.bharath.entites.Project;
import com.bharath.repo.ProjectRepository;
import com.bharath.service.ProjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    // CREATE
    @Override
    public Project saveProject(Project project) {
        return projectRepository.save(project);
    }

    // READ ALL
    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    // READ BY ID
    @Override
    public Project getProjectById(Long id) {
        return projectRepository.findById(id).orElse(null);
    }

    // FIND BY NAME
    @Override
    public List<Project> getProjectsByName(String name) {
        return projectRepository.findByProjectName(name);
    }

    // DELETE
    @Override
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }
}