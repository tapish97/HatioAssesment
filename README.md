# **Todo Project Manager**

## **Overview**
Todo Project Manager is a full-stack application that allows users to manage projects and tasks (todos). Key features include:

- User authentication.
- Project creation, editing, and deletion.
- Task management (add, update, mark as complete/pending).
- Exporting project summaries as markdown files or secret GitHub Gists.

---

## **Setup Instructions**

### **Database Setup**
1. Install Oracle Database.
2. Create a database/schema for the application. 
3. Update `TodoProjectManager\src\main\resources\application.properties` in the backend:

   ```properties
   spring.datasource.url=jdbc:oracle:thin:@<your_database_url>
   spring.datasource.username=<your_username>
   spring.datasource.password=<your_password>
   spring.jpa.hibernate.ddl-auto=update

---

## Prerequisites
### Frontend:
- Install **Node.js**.

### Backend:
- Install **Java 17**.
- Install **Maven**.
- Set up and run an **Oracle database**.

---

### Step-by-Step Instructions

#### Backend Setup: TodoProjectManager


1. **Navigate to TodoManagerProject folder**
```
cd TodoManagerProject
```
2. **Install Dependencies**
To install all required dependencies, run the following command:  
  ```env
  mvn clean install
```
3. **Start the Spring Boot Application**
Use the following command to start the Spring Boot application:
 ```env
  mvn spring-boot:run
```
4. **Test the Backend**
To ensure the backend is functioning correctly, run:
```
mvn test
```
The backend runs on port 8080

----
#### Frontend Setup: frontend-todo

1. **To generate a GitHub token:**

- Go to GitHub Developer Settings.
- Create a fine-grained token with ***Gist read and write permissions***.
- Refer [Creating a fine-grained personal access token](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/managing-your-personal-access-tokens#creating-a-fine-grained-personal-access-token)

#### Add GitHub Token
- Create a `.env` file in the root directory. **frontend-todo\.env**
- Add the following line:

  ```
   REACT_APP_GITHUB_TOKEN="<your_github_personal_access_token>"

  ```

2. **Navigate to ffrontend-todo folder**
```
cd frontend-todo
```
3. **Install Dependencies**
To install all required dependencies, run the following command:  
  ```
  npm install
```
4. **Start the React Application**
Use the following command to start the Frontend:
 ```
  npm start
```

---

## Backend APIs Summary

| **Endpoint**                | **Method** | **Description**                         |
|-----------------------------|------------|-----------------------------------------|
| `/api/auth/register`        | POST       | Register a new user.                    |
| `/api/auth/login`           | POST       | Log in and start a session.             |
| `/api/auth/logout`          | POST       | Log out and end the session.            |
| `/api/projects`             | POST       | Create a new project.                   |
| `/api/projects`             | GET        | Retrieve all projects for the logged-in user. |
| `/api/projects/{id}`        | PUT        | Update a project.                       |
| `/api/projects/{id}`        | DELETE     | Delete a project.                       |
| `/api/todos/{projectId}`    | POST       | Create a new todo for a project.        |
| `/api/todos/project/{projectId}` | GET    | Retrieve all todos for a project.       |
| `/api/todos/{id}`           | GET        | Retrieve a todo by its ID.              |
| `/api/todos/{id}`           | PUT        | Update a todo by its ID.                |
| `/api/todos/{id}`           | DELETE     | Delete a todo by its ID.                |



## Usage
- Open application in your browser.
  1. Frontend: http://localhost:3000
  2. Backend: http://localhost:8080
- Log in or register a new account.
- Create, view, and manage projects and todos.
- Export project summaries as markdown files or GitHub Gists.

