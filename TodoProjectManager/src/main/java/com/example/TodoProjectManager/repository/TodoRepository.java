package com.example.TodoProjectManager.repository;

import com.example.TodoProjectManager.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    // Custom method to find all todos by project ID
    List<Todo> findByProjectId(Long projectId);

    // Custom method to find todos by status within a specific project
    List<Todo> findByProjectIdAndStatus(Long projectId, String status);

    // Fetch Todos by ProjectsID
    @Query("SELECT t FROM Todo t WHERE t.project.id = :projectId")
    List<Todo> findTodosByProjectId(@Param("projectId") Long projectId);

    // Fetch todos by user ID (through project relationship)
    @Query("SELECT t FROM Todo t WHERE t.project.createdBy.id = :userId")
    List<Todo> findTodosByUserId(@Param("userId") Long userId);

}
