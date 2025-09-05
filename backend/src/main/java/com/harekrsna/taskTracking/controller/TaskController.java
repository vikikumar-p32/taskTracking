package com.harekrsna.taskTracking.controller;

import com.harekrsna.taskTracking.dto.TaskRequestDTO;
import com.harekrsna.taskTracking.dto.TaskResponseDTO;
import com.harekrsna.taskTracking.entity.Task;
import com.harekrsna.taskTracking.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/task-lists/{task_list_id}/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> listTasks(@PathVariable("task_list_id") UUID taskListId) {
        return ResponseEntity.status(HttpStatus.OK).body(
                taskService.listTasks(taskListId)
                        .stream()
                        .map(task -> modelMapper.map(task, TaskResponseDTO.class))
                        .toList()
        );
    }

    @GetMapping("/{task_id}")
    public ResponseEntity<TaskResponseDTO> getTask(
            @PathVariable("task_list_id") UUID taskListId,
            @PathVariable("task_id") UUID taskId) {
        return ResponseEntity.ok(modelMapper.map(taskService.getTask(taskListId, taskId), TaskResponseDTO.class));
    }

    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(
            @PathVariable("task_list_id") UUID taskListId,
            @RequestBody TaskRequestDTO taskRequestDTO) {
        Task createdTask = taskService.createTask(taskListId, modelMapper.map(taskRequestDTO, Task.class));
        return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(createdTask, TaskResponseDTO.class));
    }

    @PutMapping("/{task_id}")
    public ResponseEntity<TaskResponseDTO> updateTask(
            @PathVariable("task_list_id") UUID taskListId,
            @PathVariable("task_id") UUID taskId,
            @RequestBody TaskRequestDTO taskRequestDTO) {
        return ResponseEntity.ok(modelMapper.map(taskService.updateTask(taskListId, taskId, modelMapper.map(taskRequestDTO, Task.class)), TaskResponseDTO.class));
    }
}
