package com.example.TodoProjectManager.service;

import com.example.TodoProjectManager.model.Project;
import com.example.TodoProjectManager.model.Todo;
import com.example.TodoProjectManager.repository.ProjectRepository;
import com.example.TodoProjectManager.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private TodoService todoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTodo() {
        Long projectId = 1L;
        Project project = new Project();
        project.setId(projectId);

        Todo todo = new Todo();
        todo.setDescription("Test Todo");

        when(projectRepository.findById(projectId)).thenReturn(Optional.of(project));
        when(todoRepository.save(any(Todo.class))).thenReturn(todo);

        Todo result = todoService.createTodo(todo, projectId);

        assertNotNull(result);
        assertEquals("Test Todo", result.getDescription());
        verify(projectRepository, times(1)).findById(projectId);
        verify(todoRepository, times(1)).save(todo);
    }

    @Test
    void testCreateTodoProjectNotFound() {
        Long projectId = 1L;
        Todo todo = new Todo();

        when(projectRepository.findById(projectId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> todoService.createTodo(todo, projectId));
        verify(todoRepository, never()).save(any(Todo.class));
    }

    @Test
    void testGetTodoById() {
        Long todoId = 1L;
        Todo todo = new Todo();
        todo.setId(todoId);

        when(todoRepository.findById(todoId)).thenReturn(Optional.of(todo));

        Optional<Todo> result = todoService.getTodoById(todoId);

        assertTrue(result.isPresent());
        assertEquals(todo, result.get());
        verify(todoRepository, times(1)).findById(todoId);
    }

    @Test
    void testGetTodoByIdNotFound() {
        Long todoId = 1L;

        when(todoRepository.findById(todoId)).thenReturn(Optional.empty());

        Optional<Todo> result = todoService.getTodoById(todoId);

        assertTrue(result.isEmpty());
        verify(todoRepository, times(1)).findById(todoId);
    }

    @Test
    void testUpdateTodo() {
        Long todoId = 1L;
        Todo existingTodo = new Todo();
        existingTodo.setId(todoId);
        existingTodo.setDescription("Old Description");

        Todo updatedDetails = new Todo();
        updatedDetails.setDescription("New Description");

        Todo updatedTodo = new Todo();
        updatedTodo.setId(todoId);
        updatedTodo.setDescription("New Description");

        when(todoRepository.findById(todoId)).thenReturn(Optional.of(existingTodo));
        when(todoRepository.save(existingTodo)).thenReturn(updatedTodo);

        Todo result = todoService.updateTodo(todoId, updatedDetails);

        assertNotNull(result);
        assertEquals("New Description", result.getDescription());
        verify(todoRepository, times(1)).findById(todoId);
        verify(todoRepository, times(1)).save(existingTodo);
    }

    @Test
    void testUpdateTodoNotFound() {
        Long todoId = 1L;
        Todo updatedDetails = new Todo();

        when(todoRepository.findById(todoId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> todoService.updateTodo(todoId, updatedDetails));
        verify(todoRepository, never()).save(any(Todo.class));
    }

    @Test
    void testDeleteTodo() {
        Long todoId = 1L;
        Todo todo = new Todo();
        todo.setId(todoId);

        when(todoRepository.findById(todoId)).thenReturn(Optional.of(todo));

        todoService.deleteTodoById(todoId);

        verify(todoRepository, times(1)).findById(todoId);
        verify(todoRepository, times(1)).delete(todo);
    }

    @Test
    void testDeleteTodoNotFound() {
        Long todoId = 1L;

        when(todoRepository.findById(todoId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> todoService.deleteTodoById(todoId));
        verify(todoRepository, never()).delete(any(Todo.class));
    }
}
