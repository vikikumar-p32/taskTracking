package com.harekrsna.taskTracking.dto;

import com.harekrsna.taskTracking.entity.TaskPriority;
import com.harekrsna.taskTracking.entity.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskResponseDTO {
    private UUID id;
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private TaskPriority priority;
    private TaskStatus status;
}
