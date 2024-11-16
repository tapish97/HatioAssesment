import React, { useState, useEffect } from "react";
import axios from "axios";
import { useParams } from "react-router-dom";
import Todo from "./Todo"; // Import the nested Todo component
import "../styles/ProjectDetails.css"
import ExportGistButton from "./ExportGistButton";
const ProjectDetails = ({ token }) => {
  const { id } = useParams();
  const [project, setProject] = useState(null);
  const [todos, setTodos] = useState([]);
  const [message, setMessage] = useState("");
  const [isEditingTitle, setIsEditingTitle] = useState(false);

  const fetchProjectDetails = async () => {
    try {
      const response = await axios.get(`/api/projects/${id}`, {
        headers: { Authorization: `${token}` },
      });
      setProject(response.data);
      setTodos(response.data.todos);
    } catch (err) {
      setMessage("Failed to fetch project details.");
    }
  };

  const handleTitleChange = async () => {
    try {
      await axios.put(
        `/api/projects/${id}`,
        { title: project.title },
        {
          headers: { Authorization: `${token}` },
        }
      );
      setIsEditingTitle(false);
      setMessage("Project title updated successfully!");
    } catch (err) {
      setMessage("Failed to update project title.");
    }
  };

  useEffect(() => {
    fetchProjectDetails();
  }, []);


  useEffect(() => {
    if (project) {
      setProject({ ...project, todos });
    }
  }, [todos]);


  
  return (
    <div className="project-container">
      {message && <div className="alert alert-success">{message}</div>}
      {project && (
        <>
          <div className="project-header">
            <div>
              {isEditingTitle ? (
                <div className="input-group">
                  <input
                    type="text"
                    className="form-control"
                    value={project.title}
                    onChange={(e) => setProject({ ...project, title: e.target.value })}
                  />
                  <button className="btn btn-success" onClick={handleTitleChange}>
                    Save
                  </button>
                  <button
                    className="btn btn-secondary"
                    onClick={() => setIsEditingTitle(false)}
                  >
                    Cancel
                  </button>
                </div>
              ) : (
                <h2
                  className="project-title"
                  onClick={() => setIsEditingTitle(true)}
                  style={{ cursor: "pointer" }}
                >
                  {project.title} <span className="text-muted">(Click to Edit)</span>
                </h2>
              )}
            </div>
            <div>
            <ExportGistButton project={project} />
            </div>
          </div>

          {/* Nested Todo Component */}
          <Todo projectId={id} token={token} todos={todos} setTodos={setTodos} />
        </>
      )}
    </div>
  );
};

export default ProjectDetails;
