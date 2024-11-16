package com.example.TodoProjectManager.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TodoTest {

    @Test
    void testTodoFields() {
        Todo todo = new Todo();
        todo.setId(1L);
        todo.setDescription("Test Todo");
        todo.setStatus("COMPLETED");
        todo.setCreatedDate(LocalDateTime.of(2023, 11, 1, 10, 0));
        todo.setUpdatedDate(LocalDateTime.of(2023, 11, 2, 12, 0));

        assertEquals(1L, todo.getId());
        assertEquals("Test Todo", todo.getDescription());
        assertEquals("COMPLETED", todo.getStatus());
        assertEquals(LocalDateTime.of(2023, 11, 1, 10, 0), todo.getCreatedDate());
        assertEquals(LocalDateTime.of(2023, 11, 2, 12, 0), todo.getUpdatedDate());
    }

    @Test
    void testDefaultStatusAndCreatedDate() {
        Todo todo = new Todo();
        todo.setDescription("Default Todo");

        assertEquals("PENDING", todo.getStatus());
        assertNotNull(todo.getCreatedDate());
        assertTrue(todo.getCreatedDate().isBefore(LocalDateTime.now().plusSeconds(1)));
    }

    @Test
    void testTodoProjectAssociation() {
        Project project = new Project();
        project.setId(1L);
        project.setTitle("Test Project");

        Todo todo = new Todo();
        todo.setDescription("Associated Todo");
        todo.setProject(project);

        assertEquals(project, todo.getProject());
        assertEquals("Test Project", todo.getProject().getTitle());
    }
}
