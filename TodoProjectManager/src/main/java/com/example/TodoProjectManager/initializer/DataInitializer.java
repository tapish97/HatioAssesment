package com.example.TodoProjectManager.initializer;

import com.example.TodoProjectManager.model.Project;
import com.example.TodoProjectManager.model.Todo;
import com.example.TodoProjectManager.model.User;
import com.example.TodoProjectManager.repository.ProjectRepository;
import com.example.TodoProjectManager.repository.TodoRepository;
import com.example.TodoProjectManager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import org.mindrot.jbcrypt.BCrypt;
import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TodoRepository todoRepository;
    
    @Value("${data.initializer.enabled:true}")
    private boolean isDataInitializerEnabled;


    
    @Override
    public void run(String... args) throws Exception {


        if (!isDataInitializerEnabled) {
            System.out.println("Data initialization disabled. Skipping...");
            return;
        }


        // Add Users
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(BCrypt.hashpw("admin123", BCrypt.gensalt())); // Hash password
        userRepository.save(admin);

        User user1 = new User();
        user1.setUsername("user1");
        user1.setPassword(BCrypt.hashpw("user123", BCrypt.gensalt())); // Hash password
        userRepository.save(user1);

        User user2 = new User();
        user2.setUsername("user2");
        user2.setPassword(BCrypt.hashpw("password123", BCrypt.gensalt())); // Hash password
        userRepository.save(user2);

        // Add Projects
        Project project1 = new Project();
        project1.setTitle("Project A");
        project1.setCreatedBy(admin);
        project1.setCreatedDate(LocalDateTime.now());
        projectRepository.save(project1);

        Project project2 = new Project();
        project2.setTitle("Project B");
        project2.setCreatedBy(user1);
        project2.setCreatedDate(LocalDateTime.now());
        projectRepository.save(project2);

        Project project3 = new Project();
        project3.setTitle("Project C");
        project3.setCreatedBy(user2);
        project3.setCreatedDate(LocalDateTime.now());
        projectRepository.save(project3);

        // Add Todos
        Todo todo1 = new Todo();
        todo1.setDescription("Fix bug #123");
        todo1.setStatus("PENDING");
        todo1.setCreatedDate(LocalDateTime.now());
        todo1.setProject(project1);
        todoRepository.save(todo1);

        Todo todo2 = new Todo();
        todo2.setDescription("Add new feature");
        todo2.setStatus("COMPLETED");
        todo2.setCreatedDate(LocalDateTime.now());
        todo2.setProject(project2);
        todoRepository.save(todo2);

        Todo todo3 = new Todo();
        todo3.setDescription("Write documentation");
        todo3.setStatus("IN_PROGRESS");
        todo3.setCreatedDate(LocalDateTime.now());
        todo3.setProject(project3);
        todoRepository.save(todo3);
    }
}
