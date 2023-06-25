package com.todoapplication.service.impl;

import com.todoapplication.entity.Status;
import com.todoapplication.entity.Task;
import com.todoapplication.exception.ResourceNotFoundException;
import com.todoapplication.repository.TaskRepository;
import com.todoapplication.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    @Override
    public List<Task> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        tasks.forEach((task) -> {
            if (task.getDeadline().before(Date.from(Instant.now())) && task.getStatus().equals(Status.ON_GOING)) {
                task.setStatus(Status.LATE);
            }
        });
        return tasks;
    }

    @Override
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Task", "id", id));
    }

    @Override
    public Task createTask(Task task) {
        Task newTask = Task.builder()
                .description(task.getDescription())
                .createdAt(Instant.now())
                .deadline(task.getDeadline())
                .status(Status.ON_GOING)
                .build();

        return taskRepository.save(newTask);
    }

    @Override
    public Task updateTask(Long id, Task task) {
        Task updatedTask = getTaskById(id);

        updatedTask.setDescription(task.getDescription());
        updatedTask.setDeadline(task.getDeadline());
        updatedTask.setUpdatedAt(Instant.now());

        return taskRepository.save(updatedTask);
    }

    @Override
    public void deleteTask(Long id) {
        Task task = getTaskById(id);
        taskRepository.delete(task);
    }

    @Override
    public void finishTask(Long id) {
        Task task = getTaskById(id);
        task.setStatus(Status.COMPLETED);
        taskRepository.save(task);
    }

}
