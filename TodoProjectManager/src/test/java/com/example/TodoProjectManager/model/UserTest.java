package com.example.TodoProjectManager.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testUserFields() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        user.setPassword("securePassword");

        assertEquals(1L, user.getId());
        assertEquals("testUser", user.getUsername());
        assertEquals("securePassword", user.getPassword());
    }

    @Test
    void testUserProjectsAssociation() {
        Project project1 = new Project();
        project1.setId(1L);
        project1.setTitle("Project 1");

        Project project2 = new Project();
        project2.setId(2L);
        project2.setTitle("Project 2");

        User user = new User();
        user.getProjects().add(project1);
        user.getProjects().add(project2);

        List<Project> projects = user.getProjects();

        assertEquals(2, projects.size());
        assertEquals("Project 1", projects.get(0).getTitle());
        assertEquals("Project 2", projects.get(1).getTitle());
    }

    @Test
    void testUserAddAndRemoveProjects() {
        User user = new User();

        Project project = new Project();
        project.setId(1L);
        project.setTitle("Project 1");

        user.getProjects().add(project);
        assertEquals(1, user.getProjects().size());

        user.getProjects().remove(project);
        assertTrue(user.getProjects().isEmpty());
    }
}
