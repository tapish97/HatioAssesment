package com.example.TodoProjectManager.service;

import com.example.TodoProjectManager.model.Project;
import com.example.TodoProjectManager.model.User;
import com.example.TodoProjectManager.repository.ProjectRepository;
import com.example.TodoProjectManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;



    // Create a new project

    public Project createProject(Project project, Long userId) {
        // Fetch the user creating the project
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Set the createdBy and createdDate fields
        project.setCreatedBy(user);
        project.setCreatedDate(LocalDateTime.now());

        // Save the project
        return projectRepository.save(project);
    }






    // Get all projects for a specific user

    public List<Project> getProjectsByUserId(Long userId) {
        User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));
        return projectRepository.findProjectsByUserId(user.getId());
    }





    // Get a project by its ID

    public Optional<Project> getProjectById(Long projectId) {
        return projectRepository.findById(projectId);
    }






    // Update an existing project

    public Project updateProject(Long projectId, Project projectDetails, Long userId) {
        Project existingProject = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        // Ensure the logged-in user is the owner of the project
        if (!existingProject.getCreatedBy().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized: You can only update your own projects");
        }

        // Update project fields
        existingProject.setTitle(projectDetails.getTitle());
        // Note: Todos can be updated separately if needed.

        // Save updated project
        return projectRepository.save(existingProject);
    }









    // Delete a project

    public void deleteProject(Long projectId, Long userId) {
        Project existingProject = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        // Ensure the logged-in user is the owner of the project
        if (!existingProject.getCreatedBy().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized: You can only delete your own projects");
        }

        // Delete the project
        projectRepository.delete(existingProject);
    }







}
