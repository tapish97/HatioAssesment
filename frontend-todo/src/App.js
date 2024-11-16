import logo from './logo.svg';
import './App.css';
import React, { useState } from "react";
import { BrowserRouter as Router, Route, Routes, Navigate } from "react-router-dom";
import Login from "./Components/Auth/Login";
import Register from "./Components/Auth/Register";
import Projects from './Components/Project';
import ProjectDetails from './Components/ProjectDetails';
import ErrorPage from './Components/ErrorHandling/ErrorPage';
function App() {

  const [token, setToken] = useState(localStorage.getItem("token") || null);

  return (
    <Router>
      <Routes>
        {/* Redirect root to login */}
        <Route path="/" element={<Navigate to="/login" replace />} />
        <Route path="/register" element={<Register />} />
        <Route path="/login" element={<Login setToken={setToken} />} />
        <Route path="/projects" element={<Projects token={token} />} />
        <Route path="/projects/:id" element={<ProjectDetails token={token} />} />
        {/* Catch-all route for undefined paths */}
        <Route path="*" element={<ErrorPage />} />
      </Routes>
    </Router>
    )
}

export default App;
