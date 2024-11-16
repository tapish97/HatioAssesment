import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const Register = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");
  const navigate = useNavigate(); // React Router's navigate hook for redirection

  const handleRegister = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post("/api/auth/register", { username, password });
      alert("Registration successful!"); // Show success alert
      navigate("/login"); // Redirect to the login page
    } catch (error) {
      setMessage(error.response?.data?.message || "An error occurred!");
    }
  };

  return (
    <div className="auth-container">
    <h2>Register</h2>
    <form onSubmit={handleRegister}>
      <div>
        <label>Username:</label>
        <input
          type="text"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          required
        />
      </div>
      <div>
        <label>Password:</label>
        <input
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />
      </div>
      <button type="submit">Register</button>
    </form>
    {message && <p className="error-message">{message}</p>}
    <button onClick={() => navigate("/login")} className="btn btn-secondary" style={{ marginTop: "10px" }}>
      Go to Login
    </button>
  </div>
  
  );
};

export default Register;
