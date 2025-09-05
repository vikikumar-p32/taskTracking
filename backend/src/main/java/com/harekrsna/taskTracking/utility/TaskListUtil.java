package com.harekrsna.taskTracking.utility;

import com.harekrsna.taskTracking.entity.Task;
import com.harekrsna.taskTracking.entity.TaskStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskListUtil {

    public Double calculateTaskListProgress(List<Task> tasks) {
        if(!tasks.isEmpty()) {
            long completed = tasks.stream()
                    .filter(task -> TaskStatus.CLOSED == task.getStatus())
                    .count();
            return (double) completed / tasks.size();
        }
        return null;
    }
}
