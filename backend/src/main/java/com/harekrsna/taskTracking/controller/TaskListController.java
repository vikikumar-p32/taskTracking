package com.harekrsna.taskTracking.controller;

import com.harekrsna.taskTracking.dto.TaskListRequestDTO;
import com.harekrsna.taskTracking.dto.TaskListResponseDTO;
import com.harekrsna.taskTracking.entity.Task;
import com.harekrsna.taskTracking.entity.TaskList;
import com.harekrsna.taskTracking.entity.TaskStatus;
import com.harekrsna.taskTracking.service.TaskListService;
import com.harekrsna.taskTracking.utility.TaskListUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/task-lists")
@RequiredArgsConstructor
public class TaskListController {

    private final TaskListService taskListService;
    private final ModelMapper modelMapper;
    private final TaskListUtil taskListUtil;

    @GetMapping
    public ResponseEntity<List<TaskListResponseDTO>> listTaskLists() {
        return ResponseEntity.ok(taskListService.listTaskLists()
                .stream()
                .map(taskList -> {
                    TaskListResponseDTO dto = modelMapper.map(taskList, TaskListResponseDTO.class);
                    List<Task> tasks = taskList.getTasks();
                    dto.setCount(tasks.size());
                    dto.setProgress(taskListUtil.calculateTaskListProgress(tasks));
                    return dto;
                })
                .toList());
    }

    @PostMapping
    public ResponseEntity<TaskListResponseDTO> createTaskList(@RequestBody TaskListRequestDTO taskListRequestDTO) {
        TaskList taskList = modelMapper.map(taskListRequestDTO, TaskList.class);
        return ResponseEntity.status(HttpStatus.OK)
                .body((modelMapper.map(taskListService.createTaskList(taskList), TaskListResponseDTO.class)));
    }

    @GetMapping("/{task_list_id}")
    public ResponseEntity<TaskListResponseDTO> getTaskList(@PathVariable("task_list_id") UUID taskListId) {
        TaskList taskList = taskListService.getTaskList(taskListId);
        TaskListResponseDTO dto = modelMapper.map(taskList, TaskListResponseDTO.class);
        dto.setCount(dto.getTasks().size());
        dto.setProgress(taskListUtil.calculateTaskListProgress(taskList.getTasks()));
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{task_list_id}")
    public ResponseEntity<TaskListResponseDTO> updateTaskList(
            @PathVariable("task_list_id") UUID taskListId,
            @RequestBody @Valid TaskListRequestDTO taskListRequestDTO) {
        TaskList taskList = modelMapper.map(taskListRequestDTO, TaskList.class);
        return ResponseEntity.ok(modelMapper.map(taskListService.updateTaskList(taskListId, taskList), TaskListResponseDTO.class));
    }

    @DeleteMapping("/{task_list_id}")
    public ResponseEntity<?> deleteTaskList(@PathVariable("task_list_id") UUID taskListId) {
        taskListService.deleteTaskList(taskListId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
