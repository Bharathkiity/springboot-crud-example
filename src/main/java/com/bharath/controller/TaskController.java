package com.bharath.controller;

import com.bharath.entites.Task;
import com.bharath.service.TaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // CREATE
    @PostMapping
    public Task create(@RequestBody Task task) {
        return taskService.saveTask(task);
    }

    // READ ALL
    @GetMapping
    public List<Task> getAll() {
        return taskService.getAllTasks();
    }

    // READ BY ID
    @GetMapping("/{id}")
    public Task getById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    // GET TASKS BY EMPLOYEE
    // GET /tasks/employee/1
    @GetMapping("/employee/{empId}")
    public List<Task> getByEmployee(@PathVariable Long empId) {
        return taskService.getTasksByEmployeeId(empId);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "Task Deleted";
    }
}