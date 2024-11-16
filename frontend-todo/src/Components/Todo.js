import React, { useState } from "react";
import axios from "axios";
import "../styles/todo.css";

const Todo = ({ projectId, token, todos, setTodos }) => {
  const [newTodo, setNewTodo] = useState({ description: "", status: "PENDING" });
  const [message, setMessage] = useState("");
  const [editingTodoId, setEditingTodoId] = useState(null);
  const [editedTodo, setEditedTodo] = useState({ description: "", status: "PENDING" });

  const handleAddTodo = async () => {
    try {
      const response = await axios.post(`/api/todos/${projectId}`, newTodo, {
        headers: { Authorization: `${token}` },
      });
      setTodos((prevTodos) => [...prevTodos, response.data]);
      setNewTodo({ description: "", status: "PENDING" });
      setMessage("Todo added successfully!");
    } catch (err) {
      setMessage("Failed to add todo.");
    }
  };

  const handleDeleteTodo = async (todoId) => {
    try {
      await axios.delete(`/api/todos/${todoId}`, {
        headers: { Authorization: `${token}` },
      });
      setTodos((prevTodos) => prevTodos.filter((todo) => todo.id !== todoId));
      setMessage("Todo deleted successfully!");
    } catch (err) {
      setMessage("Failed to delete todo.");
    }
  };

  const handleEditTodo = (todo) => {
    setEditingTodoId(todo.id);
    setEditedTodo({ description: todo.description, status: todo.status });
  };

  const handleSaveEdit = async (todo) => {
    try {
      const updatedTodo = { ...todo, ...editedTodo };
      const response = await axios.put(`/api/todos/${todo.id}`, updatedTodo, {
        headers: { Authorization: `${token}` },
      });
      setTodos((prevTodos) =>
        prevTodos.map((t) => (t.id === todo.id ? response.data : t))
      );
      setEditingTodoId(null);
      setMessage("Todo updated successfully!");
    } catch (err) {
      setMessage("Failed to update todo.");
    }
  };

  const handleToggleStatus = async (todo) => {
    try {
      const updatedTodo = { ...todo, status: todo.status === "PENDING" ? "COMPLETED" : "PENDING" };
      const response = await axios.put(`/api/todos/${todo.id}`, updatedTodo, {
        headers: { Authorization: `${token}` },
      });
      setTodos((prevTodos) =>
        prevTodos.map((t) => (t.id === todo.id ? response.data : t))
      );
      setMessage("Todo status updated successfully!");
    } catch (err) {
      setMessage("Failed to update todo status.");
    }
  };

  const formatDateTime = (utcDate) => {
    if (!utcDate) return "N/A";
    const date = new Date(utcDate);
    return date.toLocaleString();
  };

  return (
    <div className="todo-container">
      {message && <div className="alert alert-success">{message}</div>}
      <h3>Todos</h3>
      <div className="add-todo-form">
        <h4>Add Todo task</h4>
        <div className="input-group">
          <input
            type="text"
            className="form-control"
            placeholder="Description"
            value={newTodo.description}
            onChange={(e) => setNewTodo({ ...newTodo, description: e.target.value })}
          />
          <button className="btn btn-primary" onClick={handleAddTodo}>
            Add
          </button>
        </div>
      </div>
      <br/>
      <ul className="todos-list">
        {todos.map((todo) => (
          <li className="todo-item" key={todo.id}>
            {editingTodoId === todo.id ? (
              <div className="edit-form">
                <input
                  type="text"
                  value={editedTodo.description}
                  onChange={(e) =>
                    setEditedTodo((prev) => ({ ...prev, description: e.target.value }))
                  }
                  className="form-control"
                  placeholder="Edit description"
                />
                <select
                  value={editedTodo.status}
                  onChange={(e) =>
                    setEditedTodo((prev) => ({ ...prev, status: e.target.value }))
                  }
                  className="form-select"
                >
                  <option value="PENDING">Pending</option>
                  <option value="COMPLETED">Completed</option>
                </select>
                <div className="edit-actions">
                  <button
                    className="btn btn-success btn-sm"
                    onClick={() => handleSaveEdit(todo)}
                  >
                    Save
                  </button>
                  <button
                    className="btn btn-secondary btn-sm"
                    onClick={() => setEditingTodoId(null)}
                  >
                    Cancel
                  </button>
                </div>
              </div>
            ) : (
              <>
                <p>Description: {todo.description}</p>
                <p>Status: <strong>{todo.status}</strong></p>
                <p><strong>Created:</strong> {formatDateTime(todo.createdDate)}</p>
                <p><strong>Updated:</strong> {formatDateTime(todo.updatedDate)}</p>
                <div className="todo-actions">
                  <button
                    className="btn btn-info btn-sm"
                    onClick={() => handleEditTodo(todo)}
                  >
                    Edit
                  </button>
                  <button
                    className="btn btn-success btn-sm"
                    onClick={() => handleToggleStatus(todo)}
                  >
                    {todo.status === "PENDING" ? "Mark Complete" : "Mark Pending"}
                  </button>
                  <button
                    className="btn btn-danger btn-sm"
                    onClick={() => handleDeleteTodo(todo.id)}
                  >
                    Delete
                  </button>
                </div>
              </>
            )}
          </li>
        ))}
      </ul>

      
    </div>
  );
};

export default Todo;
