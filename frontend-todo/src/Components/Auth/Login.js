import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "../../styles/Auth.css";

const Login = ({ setToken }) => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("");
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post("/api/auth/login", { username, password });
      const strtoken = response.data;
      const token = strtoken.split("Token: ")[1];
      localStorage.setItem("token", token);
      setToken(token);
      setMessage("Login successful!");
      navigate("/projects");
    } catch (error) {
      setMessage(error.response?.data?.message || "Invalid credentials!");
    }
  };

  return (
    <div className="auth-container">
  <h2>Login</h2>
  <form onSubmit={handleLogin}>
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
    <button type="submit">Login</button>
  </form>
  <p className="error-message">{message}</p>
  <hr />
  <p>Don't have an account?</p>
  <button onClick={() => navigate("/register")} className="btn btn-secondary">
    Register
  </button>
</div>

  );
};

export default Login;
