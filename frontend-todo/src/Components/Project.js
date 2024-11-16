import React, { useState, useEffect } from "react";
import axios from "axios";
import { Navigate, useNavigate } from "react-router-dom";
import "../styles/Project.css"; // Import the CSS file

const Projects = ({ token }) => {
  const [projects, setProjects] = useState([]);
  const [error, setError] = useState("");
  const [newProject, setNewProject] = useState({ title: "" });
  const [message, setMessage] = useState("");
  const navigate = useNavigate();
  const userToken = token || localStorage.getItem("token");

  const fetchProjects = async () => {
    try {
      const response = await axios.get("/api/projects", {
        headers: {
          Authorization: `${userToken}`,
        },
      });
      setProjects(response.data); // Assuming API returns an array of projects
    } catch (err) {
      setError("Failed to fetch projects. Please try again.");
    }
  };

  useEffect(() => {
    fetchProjects();
  }, [userToken]);

  const handleCreateProject = async (e) => {
    e.preventDefault();
    try {
      await axios.post(
        "/api/projects",
        {
          title: newProject.title,
        },
        {
          headers: {
            Authorization: `${userToken}`,
          },
        }
      );
      setNewProject({ title: "" }); // Reset form fields
      setMessage("Project created successfully!");
      fetchProjects(); // Re-fetch projects after creation
    } catch (err) {
      setMessage("Failed to create project. Please try again.");
    }
  };

  const handleDeleteProject = async (id) => {
    try {
      await axios.delete(`/api/projects/${id}`, {
        headers: {
          Authorization: `${userToken}`,
        },
      });
      setProjects((prevProjects) => prevProjects.filter((project) => project.id !== id)); // Remove project from state
      setMessage("Project deleted successfully!");
    } catch (err) {
      setMessage("Failed to delete project. Please try again.");
    }
  };

  const handleLogout = () => {
    localStorage.removeItem("token"); // Clear the token
    navigate("/login"); // Redirect to login page
  };

  const formatDateTime = (utcDate) => {
    const date = new Date(utcDate);
    return date.toLocaleString(); // Converts to local date and time
  };

  const countTodos = (todos, status) => {
    return todos.filter((todo) => todo.status === status).length;
  };

  return (
    <div className="projects-container">
      <div className="projects-header">
        <h2>Projects</h2>
        <button className="btn btn-danger" onClick={handleLogout}>
          Logout
        </button>
      </div>
      {error && <p className="error">{error}</p>}
      {message && <p className="message">{message}</p>}

      <div className="new-project-form card">
        <h3 className="card-header">Create New Project</h3>
        <div className="card-body">
          <form onSubmit={handleCreateProject}>
            <div className="form-group">
              <label>Title:</label>
              <input
                type="text"
                value={newProject.title}
                onChange={(e) => setNewProject({ ...newProject, title: e.target.value })}
                required
              />
            </div>
            <button type="submit" className="btn btn-primary">
              Create Project
            </button>
          </form>
        </div>
      </div>

      <div className="projects-list">
        {projects && projects.length > 0 ? (projects.map((project) => (
          <div key={project.id} className="project-card">
            <div className="project-header">
              <h3>{project.title}</h3>
              <button
                className="btn-delete"
                onClick={() => handleDeleteProject(project.id)}
                title="Delete Project"
              >
                ✖️
              </button>
            </div>
            <p><strong>Created:</strong> {formatDateTime(project.createdDate)}</p>
            <p>
              <strong>Todos:</strong> {project.todos.length} <br />
              <strong>Completed:</strong> {countTodos(project.todos, "COMPLETED")} <br />
              <strong>Pending:</strong> {countTodos(project.todos, "PENDING")}
            </p>
            <button
              className="btn btn-secondary"
              onClick={() => navigate(`/projects/${project.id}`)}
            >
              View Details
            </button>
          </div>
        ))):(<p>No Projects Available</p>)}
      </div>
    </div>
  );
};

export default Projects;
