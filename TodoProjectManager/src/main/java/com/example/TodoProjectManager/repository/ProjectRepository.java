package com.example.TodoProjectManager.repository;

import com.example.TodoProjectManager.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    // Custom method to find all projects by a specific user (creator)
    List<Project> findByCreatedByUsername(String username);

    @Query("SELECT p FROM Project p WHERE p.createdBy.id = :userId")
    List<Project> findProjectsByUserId(@Param("userId") Long userId);
}
