package com.harekrsna.taskTracking.service;

import com.harekrsna.taskTracking.dto.TaskListResponseDTO;
import com.harekrsna.taskTracking.entity.TaskList;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskListService {
    List<TaskList> listTaskLists();
    TaskList createTaskList(TaskList taskList);
    TaskList getTaskList(UUID id);
    TaskList updateTaskList(UUID id, TaskList taskList);
    void deleteTaskList(UUID id);
}
