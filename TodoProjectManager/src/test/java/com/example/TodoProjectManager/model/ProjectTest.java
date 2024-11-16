package com.example.TodoProjectManager.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProjectTest {

    @Test
    void testProjectFields() {
        Project project = new Project();
        project.setId(1L);
        project.setTitle("Test Project");
        project.setCreatedDate(LocalDateTime.now());

        assertEquals(1L, project.getId());
        assertEquals("Test Project", project.getTitle());
        assertNotNull(project.getCreatedDate());
    }

    @Test
    void testProjectCreatedBy() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");

        Project project = new Project();
        project.setCreatedBy(user);

        assertEquals(user, project.getCreatedBy());
        assertEquals("testUser", project.getCreatedBy().getUsername());
    }

    @Test
    void testProjectTodos() {
        Todo todo1 = new Todo();
        todo1.setId(1L);
        todo1.setDescription("Todo 1");

        Todo todo2 = new Todo();
        todo2.setId(2L);
        todo2.setDescription("Todo 2");

        Project project = new Project();
        project.getTodos().add(todo1);
        project.getTodos().add(todo2);

        List<Todo> todos = project.getTodos();

        assertEquals(2, todos.size());
        assertEquals("Todo 1", todos.get(0).getDescription());
        assertEquals("Todo 2", todos.get(1).getDescription());
    }

    @Test
    void testProjectAddAndRemoveTodos() {
        Project project = new Project();

        Todo todo = new Todo();
        todo.setId(1L);
        todo.setDescription("Todo 1");

        project.getTodos().add(todo);
        assertEquals(1, project.getTodos().size());

        project.getTodos().remove(todo);
        assertTrue(project.getTodos().isEmpty());
    }
}
