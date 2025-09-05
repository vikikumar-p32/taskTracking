package com.harekrsna.taskTracking.service.impl;

import com.harekrsna.taskTracking.entity.Task;
import com.harekrsna.taskTracking.entity.TaskList;
import com.harekrsna.taskTracking.entity.TaskStatus;
import com.harekrsna.taskTracking.exception.ResourceNotFoundException;
import com.harekrsna.taskTracking.repository.TaskListRepository;
import com.harekrsna.taskTracking.service.TaskListService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskListServiceImpl implements TaskListService {

    private final TaskListRepository taskListRepository;

    @Override
    public List<TaskList> listTaskLists() {
        return taskListRepository.findAll();
    }

    @Override
    public TaskList createTaskList(TaskList taskList) {
        if(taskList.getTitle().isEmpty()) throw new IllegalArgumentException("TaskList title is required!");
        LocalDateTime now = LocalDateTime.now();
        taskList.setCreatedAt(now);
        taskList.setUpdatedAt(now);
        return taskListRepository.save(taskList);
    }

    @Override
    public TaskList getTaskList(UUID id) {
        return taskListRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TaskList not found with id: " + id));
    }

    @Override
    public TaskList updateTaskList(UUID id, TaskList taskList) {
        TaskList existingTaskList = taskListRepository.findById(id).orElse(null);
        if(existingTaskList == null) throw new ResourceNotFoundException("TaskList not found with id: " + id);
        if(taskList.getTitle().isEmpty()) throw new IllegalArgumentException("TaskList title is required!");
        existingTaskList.setTitle(taskList.getTitle());
        existingTaskList.setDescription(taskList.getDescription());
        existingTaskList.setTasks(taskList.getTasks());
        return taskListRepository.save(existingTaskList);
    }

    @Override
    public void deleteTaskList(UUID id) {
        taskListRepository.delete(getTaskList(id));
    }
}
