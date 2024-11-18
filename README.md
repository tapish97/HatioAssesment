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
3. Update `src/main/resources/application.properties` in the backend:
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

## Backend Setup: TodoProjectManager

### Prerequisites
- Install **Java 17**.
- Install **Maven**.
- Set up and run an **Oracle database**.

### Step-by-Step Instructions


### **Add GitHub Token**

1. Create a `.env` file in the root directory.
2. frontend-todo\.env
3. Add the following line:
   ```
   REACT_APP_GITHUB_TOKEN=<your_github_personal_access_token>
   ```
### **To generate a GitHub token:**

1. Go to GitHub Developer Settings.
2. Create a fine-grained token with Gist write permissions.


### Backend Setup: TodoProjectManager

1. **Clone the Backend Repository**
   ```bash
   git clone <repository_url>
   cd TodoProjectManager

#### Navigate to TodoManagerProject folder
```
cd TodoManagerProject
```
#### Install Dependencies
To install all required dependencies, run the following command:  
  ```env
  mvn clean install
```
### Start the Spring Boot Application
Use the following command to start the Spring Boot application:
 ```env
  mvn spring-boot:run
```
----
### Frontend Setup: frontend-todo

#### Navigate to TodoManagerProject folder
```
cd frontend-todo
```
#### Install Dependencies
To install all required dependencies, run the following command:  
  ```
  npm install
```
### Start the React Application
Use the following command to start the Frontend:
 ```
  npm start
```
---
## Usage
-- Open the frontend at http://localhost:3000.
-- Log in or register a new account.
-- Create, view, and manage projects and todos.
-- Export project summaries as markdown files or GitHub Gists.

