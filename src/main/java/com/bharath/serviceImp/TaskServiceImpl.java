package com.bharath.serviceImp;

import com.bharath.entites.Task;
import com.bharath.repo.TaskRepository;
import com.bharath.service.TaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    // CREATE
    @Override
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    // READ ALL
    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    // READ BY ID
    @Override
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    // FIND TASKS BY EMPLOYEE
    @Override
    public List<Task> getTasksByEmployeeId(Long employeeId) {
        return taskRepository.findByEmployeeId(employeeId);
    }

    // DELETE
    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}