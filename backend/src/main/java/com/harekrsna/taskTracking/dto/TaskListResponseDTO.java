package com.harekrsna.taskTracking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskListResponseDTO {
    private UUID id;
    private String title;
    private String description;
    private Integer count;
    private Double progress;
    private List<TaskResponseDTO> tasks;


}
