package com.example.TodoProjectManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.example.TodoProjectManager.filter.SessionFilter;

// import com.example.TodoProjectManager.filter.SessionFilter;


@SpringBootApplication
public class TodoProjectManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoProjectManagerApplication.class, args);
	}
//  @Bean
//     public FilterRegistrationBean<SessionFilter> sessionFilter() {
//         FilterRegistrationBean<SessionFilter> registrationBean = new FilterRegistrationBean<>();
//         registrationBean.setFilter(new SessionFilter());
//         registrationBean.addUrlPatterns("/api/*");
//         return registrationBean;
//     }

    
}
