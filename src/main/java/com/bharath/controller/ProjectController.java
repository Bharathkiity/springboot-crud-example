package com.bharath.controller;

import com.bharath.entites.Project;
import com.bharath.service.ProjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    // CREATE
    @PostMapping
    public Project create(@RequestBody Project project) {
        return projectService.saveProject(project);
    }

    // READ ALL
    @GetMapping
    public List<Project> getAll() {
        return projectService.getAllProjects();
    }

    // READ BY ID
    @GetMapping("/{id}")
    public Project getById(@PathVariable Long id) {
        return projectService.getProjectById(id);
    }

    // SEARCH BY NAME
    // GET /projects/search?name=BankingApp
    @GetMapping("/search")
    public List<Project> getByName(@RequestParam String name) {
        return projectService.getProjectsByName(name);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        projectService.deleteProject(id);
        return "Project Deleted";
    }
}