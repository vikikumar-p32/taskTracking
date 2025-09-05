package com.harekrsna.taskTracking.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskListRequestDTO {
    @NotBlank(message = "Title must not be blank")
    private String title;
    private String description;
    private Integer count;
    private Double progress;
    private List<TaskResponseDTO> tasks;
}
