package com.harekrsna.taskTracking.service.impl;

import com.harekrsna.taskTracking.entity.Task;
import com.harekrsna.taskTracking.entity.TaskList;
import com.harekrsna.taskTracking.entity.TaskPriority;
import com.harekrsna.taskTracking.entity.TaskStatus;
import com.harekrsna.taskTracking.exception.ResourceNotFoundException;
import com.harekrsna.taskTracking.repository.TaskListRepository;
import com.harekrsna.taskTracking.repository.TaskRepository;
import com.harekrsna.taskTracking.service.TaskListService;
import com.harekrsna.taskTracking.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskListRepository taskListRepository;

    private final TaskListService taskListService;

    @Override
    public List<Task> listTasks(UUID taskListId) {
        if(!taskListRepository.existsById(taskListId)) throw new ResourceNotFoundException("No TaskList exist with given taskListId: " + taskListId);
        return taskRepository.findByTaskListId(taskListId);
    }

    @Override
    public Task createTask(UUID taskListId, Task task) {
        TaskList taskList = taskListService.getTaskList(taskListId);
        TaskPriority taskPriority = Optional.ofNullable(task.getPriority())
                .orElse(TaskPriority.MEDIUM);
        task.setPriority(taskPriority); // default MEDIUM
        task.setStatus(TaskStatus.OPEN); // default OPEN
        LocalDateTime now = LocalDateTime.now();
        task.setCreatedAt(now);
        task.setUpdatedAt(now);
        task.setTaskList(taskList); // for establishing the relationship b/w task and taskList
        return taskRepository.save(task);
    }

    @Override
    public Task getTask(UUID taskListId, UUID taskId) {
        if(!taskListRepository.existsById(taskListId)) throw new ResourceNotFoundException("No TaskList exist with given taskListId: " + taskListId);
        if(!taskRepository.existsById(taskId)) throw new ResourceNotFoundException("No Task exist with given taskId: " + taskId);
        return taskRepository.findByTaskListIdAndId(taskListId, taskId);
    }

    @Override
    @Transactional
    public Task updateTask(UUID taskListId, UUID taskId, Task task) {
        if(!taskListRepository.existsById(taskListId)) throw new ResourceNotFoundException("No TaskList exist with given taskListId: " + taskListId);
        if(!taskRepository.existsById(taskId)) throw new ResourceNotFoundException("No Task exist with given taskId: " + taskId);
        Task existingTask = taskRepository.findByTaskListIdAndId(taskListId, taskId);
        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setDueDate(task.getDueDate());
        existingTask.setPriority(task.getPriority());
        existingTask.setStatus(task.getStatus());
        existingTask.setUpdatedAt(LocalDateTime.now());
        return taskRepository.save(existingTask);
    }

    @Override
    @Transactional
    public void deleteTask(UUID taskListId, UUID taskId) {
        if(!taskListRepository.existsById(taskListId)) throw new ResourceNotFoundException("No TaskList exist with given taskListId: " + taskListId);
        if(!taskRepository.existsById(taskId)) throw new ResourceNotFoundException("No Task exist with given taskId: " + taskId);
        taskRepository.deleteByTaskListIdAndId(taskListId, taskId);
    }
}
