package com.example.TodoProjectManager.controller;

import com.example.TodoProjectManager.model.Project;
import com.example.TodoProjectManager.model.User;
import com.example.TodoProjectManager.service.ProjectService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;




    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project, HttpServletRequest request) {
        Long userId = getUserIdFromToken(request); // Extract user ID from token/session
        Project createdProject = projectService.createProject(project, userId);
        return new ResponseEntity<>(createdProject, HttpStatus.CREATED);
    }

    
    
    
    
    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects(HttpServletRequest request) {
        Long userId = getUserIdFromToken(request);
        List<Project> projects = projectService.getProjectsByUserId(userId);
        if (projects.isEmpty()) {
            return ResponseEntity.noContent().build(); // Return 204 No Content if no projects found
        }
        return ResponseEntity.ok(projects); // Return 200 OK with the list of projects
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Project>> getProjectById(@PathVariable Long id, HttpServletRequest request) {
        Long userId = getUserIdFromToken(request); // Extract the user ID from session
        Optional<Project> project = projectService.getProjectById(id); // Retrieve project for this user and ID
        if (project == null || userId==null ) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Return 404 if not found
        }
        return ResponseEntity.ok(project); // Return the project details
    }
    
    
    
    
    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody Project projectDetails, HttpServletRequest request) {
        Long userId = getUserIdFromToken(request);
        Project updatedProject = projectService.updateProject(id, projectDetails, userId);
        return ResponseEntity.ok(updatedProject);
    }

    
    
    
    
    
    
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id, HttpServletRequest request) {
        Long userId = getUserIdFromToken(request);
        projectService.deleteProject(id, userId);
        return ResponseEntity.noContent().build();
    }

    
    
    
    
    
    
    
    
    
    
    
    private Long getUserIdFromToken(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            throw new RuntimeException("Unauthorized: User is not logged in");
        }

        return ((User) session.getAttribute("user")).getId(); // Replace `YourUserClass` with your actual User class
    }
}
