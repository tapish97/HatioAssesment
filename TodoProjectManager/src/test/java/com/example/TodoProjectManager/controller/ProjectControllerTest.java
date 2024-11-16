package com.example.TodoProjectManager.controller;

import com.example.TodoProjectManager.model.Project;
import com.example.TodoProjectManager.model.User;
import com.example.TodoProjectManager.service.ProjectService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProjectControllerTest {

    @Mock
    private ProjectService projectService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @InjectMocks
    private ProjectController projectController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createProjectTest() {
        Project project = new Project();
        project.setTitle("Test Project");
        User user = new User();
        user.setId(1L);

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(projectService.createProject(project, user.getId())).thenReturn(project);

        ResponseEntity<Project> response = projectController.createProject(project, request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(project, response.getBody());
    }

    @Test
    void getAllProjectsTest() {
        User user = new User();
        user.setId(1L);
        List<Project> projects = List.of(new Project(), new Project());

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(projectService.getProjectsByUserId(user.getId())).thenReturn(projects);

        ResponseEntity<List<Project>> response = projectController.getAllProjects(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projects, response.getBody());
    }

    @Test
    void getProjectByIdTest() {
        Long projectId = 1L;
        Project project = new Project();
        project.setId(projectId);
        User user = new User();
        user.setId(1L);

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(projectService.getProjectById(projectId)).thenReturn(Optional.of(project));

        ResponseEntity<Optional<Project>> response = projectController.getProjectById(projectId, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(Optional.of(project), response.getBody());
    }

    @Test
    void updateProjectTest() {
        Long projectId = 1L;
        Project projectDetails = new Project();
        projectDetails.setTitle("Updated Project");
        Project updatedProject = new Project();
        updatedProject.setId(projectId);
        updatedProject.setTitle("Updated Project");
        User user = new User();
        user.setId(1L);

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(projectService.updateProject(projectId, projectDetails, user.getId())).thenReturn(updatedProject);

        ResponseEntity<Project> response = projectController.updateProject(projectId, projectDetails, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedProject, response.getBody());
    }

    @Test
    void deleteProjectTest() {
        Long projectId = 1L;
        User user = new User();
        user.setId(1L);

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);

        ResponseEntity<Void> response = projectController.deleteProject(projectId, request);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(projectService, times(1)).deleteProject(projectId, user.getId());
    }
}
