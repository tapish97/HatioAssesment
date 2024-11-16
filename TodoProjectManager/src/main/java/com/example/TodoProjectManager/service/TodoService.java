package com.example.TodoProjectManager.service;

import com.example.TodoProjectManager.model.Project;
import com.example.TodoProjectManager.model.Todo;
import com.example.TodoProjectManager.repository.ProjectRepository;
import com.example.TodoProjectManager.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private ProjectRepository projectRepository;




    // Create a new Todo
    public Todo createTodo(Todo todo, Long projectId) {
        // Validate that the project exists
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        // Link the todo to the project and set creation date
        todo.setProject(project);
        todo.setCreatedDate(LocalDateTime.now());

        return todoRepository.save(todo);
    }




    // Get all Todos for a specific Project
    public List<Todo> getTodosByProjectId(Long projectId) {
        return todoRepository.findByProjectId(projectId);
    }




    // Get a Todo by ID
    public Optional<Todo> getTodoById(Long id) {
        return todoRepository.findById(id);
    }




    // Update a Todo
    public Todo updateTodo(Long todoId, Todo todoDetails) {
        Todo existingTodo = todoRepository.findById(todoId)
                .orElseThrow(() -> new RuntimeException("Todo not found"));

            // Update the fields
        existingTodo.setDescription(todoDetails.getDescription());
        existingTodo.setStatus(todoDetails.getStatus());
        existingTodo.setUpdatedDate(LocalDateTime.now());

        return todoRepository.save(existingTodo);
    }




    // Delete a Todo
    public void deleteTodoById(Long todoId) {
        Todo existingTodo = todoRepository.findById(todoId)
                .orElseThrow(() -> new RuntimeException("Todo not found"));

        todoRepository.delete(existingTodo);
    }






}
