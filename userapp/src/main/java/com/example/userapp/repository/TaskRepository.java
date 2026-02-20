package com.example.userapp.repository;

import com.example.userapp.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t FROM Task t LEFT JOIN FETCH t.assignees WHERE t.id = :id")
    Optional<Task> findByIdWithAssignees(@Param("id") Long id);

    List<Task> findByOwnerId(Long ownerId);
}