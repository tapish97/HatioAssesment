package com.example.TodoProjectManager.controller;

import com.example.TodoProjectManager.model.Todo;
import com.example.TodoProjectManager.model.User;
import com.example.TodoProjectManager.service.TodoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TodoControllerTest {

    @Mock
    private TodoService todoService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @InjectMocks
    private TodoController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTodo() {
        Long projectId = 1L;
        Todo todo = new Todo();
        todo.setDescription("Test Todo");
        User user = new User();
        user.setId(1L);

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(todoService.createTodo(todo, projectId)).thenReturn(todo);

        ResponseEntity<Todo> response = controller.createTodo(projectId, todo, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(todo, response.getBody());
        verify(todoService, times(1)).createTodo(todo, projectId);
    }

    @Test
    void testGetTodoById() {
        Long todoId = 1L;
        Todo todo = new Todo();
        todo.setId(todoId);
        User user = new User();
        user.setId(1L);

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(todoService.getTodoById(todoId)).thenReturn(Optional.of(todo));

        ResponseEntity<?> response = controller.getTodoById(todoId, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(todo, response.getBody());
    }

    @Test
    void testGetTodoByIdUnauthorized() {
        Long todoId = 1L;

        when(request.getSession(false)).thenReturn(null);

        ResponseEntity<?> response = controller.getTodoById(todoId, request);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Unauthorized: User is not logged in", response.getBody());
    }

    @Test
    void testUpdateTodo() {
        Long todoId = 1L;
        Todo todoDetails = new Todo();
        todoDetails.setDescription("Updated Todo");
        Todo updatedTodo = new Todo();
        updatedTodo.setId(todoId);
        updatedTodo.setDescription("Updated Todo");
        User user = new User();
        user.setId(1L);

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(todoService.updateTodo(todoId, todoDetails)).thenReturn(updatedTodo);

        ResponseEntity<?> response = controller.updateTodo(todoId, todoDetails, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedTodo, response.getBody());
    }

    @Test
    void testDeleteTodo() {
        Long todoId = 1L;
        User user = new User();
        user.setId(1L);

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);

        ResponseEntity<?> response = controller.deleteTodoById(todoId, request);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(todoService, times(1)).deleteTodoById(todoId);
    }

    @Test
    void testDeleteTodoUnauthorized() {
        Long todoId = 1L;

        when(request.getSession(false)).thenReturn(null);

        ResponseEntity<?> response = controller.deleteTodoById(todoId, request);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Unauthorized: User is not logged in", response.getBody());
    }
}
