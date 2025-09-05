package com.harekrsna.taskTracking.repository;

import com.harekrsna.taskTracking.entity.TaskList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TaskListRepository extends JpaRepository<TaskList, UUID> {
    boolean existsById(@NonNull UUID id);
}
