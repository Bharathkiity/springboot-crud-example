package com.bharath.service;

import com.bharath.entites.Task;

import java.util.List;

public interface TaskService {

    Task saveTask(Task task);

    List<Task> getAllTasks();

    Task getTaskById(Long id);

    List<Task> getTasksByEmployeeId(Long employeeId);

    void deleteTask(Long id);
}