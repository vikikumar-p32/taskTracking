package com.harekrsna.taskTracking.repository;

import com.harekrsna.taskTracking.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

//    Spring Data JPA hamesha boolean check methods deta hai i.e. boolean existsBy<FieldName>(FieldType fieldName);
    boolean existsById(@NonNull UUID taskId);

    List<Task> findByTaskListId(UUID taskListId);
    Task findByTaskListIdAndId(UUID taskListId, UUID taskId);
    void deleteByTaskListIdAndId(UUID taskListId, UUID taskId);
}
