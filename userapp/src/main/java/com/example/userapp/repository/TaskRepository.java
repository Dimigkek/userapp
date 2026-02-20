package com.example.userapp.repository;

import com.example.userapp.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List; // Προσθήκη Import
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("SELECT t FROM Task t LEFT JOIN FETCH t.assignees WHERE t.id = :id")
    Optional<Task> findByIdWithAssignees(@Param("id") Long id);


    List<Task> findAllByOwnerId(Long ownerId);


    Page<Task> findByOwnerId(Long ownerId, Pageable pageable);

    Page<Task> findByAssigneesId(Long userId, Pageable pageable);
}