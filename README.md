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


# Backend Setup: TodoProjectManager

## Prerequisites
- Install **Java 17**.
- Install **Maven**.
- Set up and run an **Oracle database**.

---

## Step-by-Step Instructions


