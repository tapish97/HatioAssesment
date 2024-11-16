package com.example.TodoProjectManager.service;

import com.example.TodoProjectManager.model.Project;
import com.example.TodoProjectManager.model.User;
import com.example.TodoProjectManager.repository.ProjectRepository;
import com.example.TodoProjectManager.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ProjectService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateProject() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        Project project = new Project();
        project.setTitle("Test");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(projectRepository.save(any(Project.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Project result = service.createProject(project, userId);

        assertNotNull(result);
        assertEquals("Test", result.getTitle());
        assertEquals(user, result.getCreatedBy());
        verify(userRepository, times(1)).findById(userId);
        verify(projectRepository, times(1)).save(project);
    }

    @Test
    void testCreateProjectUserNotFound() {
        Long userId = 1L;
        Project project = new Project();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.createProject(project, userId));
        verify(projectRepository, never()).save(any(Project.class));
    }

    @Test
    void testGetProjectsByUserId() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        List<Project> projects = List.of(new Project(), new Project());

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(projectRepository.findProjectsByUserId(userId)).thenReturn(projects);

        List<Project> result = service.getProjectsByUserId(userId);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userRepository, times(1)).findById(userId);
        verify(projectRepository, times(1)).findProjectsByUserId(userId);
    }

    @Test
    void testGetProjectsByUserIdUserNotFound() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.getProjectsByUserId(userId));
        verify(projectRepository, never()).findProjectsByUserId(anyLong());
    }

    @Test
    void testGetProjectById() {
        Long projectId = 1L;
        Project project = new Project();
        project.setId(projectId);

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        Optional<Project> result = service.getProjectById(projectId);

        assertTrue(result.isPresent());
        assertEquals(project, result.get());
        verify(projectRepository, times(1)).findById(projectId);
    }

    @Test
    void testGetProjectByIdNotFound() {
        Long projectId = 1L;

        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        Optional<Project> result = service.getProjectById(projectId);

        assertTrue(result.isEmpty());
        verify(projectRepository, times(1)).findById(projectId);
    }

    @Test
    void testUpdateProject() {
        Long projectId = 1L;
        Long userId = 1L;

        User user = new User();
        user.setId(userId);

        Project project = new Project();
        project.setId(projectId);
        project.setTitle("Old Title");
        project.setCreatedBy(user);

        Project updatedDetails = new Project();
        updatedDetails.setTitle("New Title");

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(projectRepository.save(project)).thenAnswer(invocation -> invocation.getArgument(0));

        Project result = service.updateProject(projectId, updatedDetails, userId);

        assertNotNull(result);
        assertEquals("New Title", result.getTitle());
        verify(projectRepository, times(1)).findById(projectId);
        verify(projectRepository, times(1)).save(project);
    }

    @Test
    void testUpdateProjectUnauthorized() {
        Long projectId = 1L;
        Long userId = 2L;

        User owner = new User();
        owner.setId(1L);

        Project project = new Project();
        project.setId(projectId);
        project.setCreatedBy(owner);

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        assertThrows(RuntimeException.class, () -> service.updateProject(projectId, new Project(), userId));
        verify(projectRepository, never()).save(any(Project.class));
    }

    @Test
    void testDeleteProject() {
        Long projectId = 1L;
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        Project project = new Project();
        project.setId(projectId);
        project.setCreatedBy(user);

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        service.deleteProject(projectId, userId);

        verify(projectRepository, times(1)).findById(projectId);
        verify(projectRepository, times(1)).delete(project);
    }

    @Test
    void testDeleteProjectUnauthorized() {
        Long projectId = 1L;
        Long userId = 2L;

        User owner = new User();
        owner.setId(1L);

        Project project = new Project();
        project.setId(projectId);
        project.setCreatedBy(owner);

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));

        assertThrows(RuntimeException.class, () -> service.deleteProject(projectId, userId));
        verify(projectRepository, never()).delete(any(Project.class));
    }
}
