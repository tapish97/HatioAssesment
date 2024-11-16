package com.example.TodoProjectManager.controller;

import com.example.TodoProjectManager.model.Todo;
import com.example.TodoProjectManager.model.User;
import com.example.TodoProjectManager.service.TodoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping("/api/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    // Create a new Todo
    @PostMapping("/{projectId}")
    public ResponseEntity<Todo> createTodo(@PathVariable Long projectId, @RequestBody Todo todo, HttpServletRequest request) {
        Long userId = getUserIdFromSession(request); // Ensure the user is logged in
        Todo createdTodo = todoService.createTodo(todo, projectId);
        return ResponseEntity.ok(createdTodo);
    }


    // Get a Todo by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getTodoById(@PathVariable Long id, HttpServletRequest request) {
        try{   Long userId = getUserIdFromSession(request);
        }catch(RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }   
        return todoService.getTodoById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }



    // Update a Todo
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTodo(@PathVariable Long id, @RequestBody Todo todoDetails, HttpServletRequest request) {
        try {
            long userId = getUserIdFromSession(request); // Ensure the user is logged in
        }
        catch(RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
            Todo updatedTodo = todoService.updateTodo(id, todoDetails);
            return ResponseEntity.ok(updatedTodo);
        
    }



    // Delete a Todo
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTodoById(@PathVariable Long id, HttpServletRequest request) {
    try{   
        Long userId = getUserIdFromSession(request);
    }catch(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    } 
    // Ensure the user is logged in
        todoService.deleteTodoById(id);
        return ResponseEntity.noContent().build();
    }


    // Helper method to check if the user is logged in and get their ID
    private Long getUserIdFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            throw new RuntimeException("Unauthorized: User is not logged in");
        }

        return ((User) session.getAttribute("user")).getId(); // Replace `YourUserClass` with your actual User class
    }
}
