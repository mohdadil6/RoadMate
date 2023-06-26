import React, { useState } from 'react';
import './App.css';
import { Link, useNavigate } from "react-router-dom";
import { toast,ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

function Signup() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();

  const handleFormSubmit = (e) => {
    e.preventDefault();

    // send a POST request to the backend API
    fetch("/api/riders", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({ email, password })
    })
      .then((response) => {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        return response.json();
      })
      .then((data) => {
        // handle successful response from the backend
        console.log(data);
        toast.success('Congratuations ! Signup successful');
        navigate('/login'); // redirect to login page
      })
      .catch((error) => {
        // handle error from the backend
        console.error('There was a problem with the fetch operation:', error);
        toast.error('There was an error signing up. Email already exists.');
      });
  };

  return (
    <div id="signup-form">
      <ToastContainer/>
      <h1>Sign Up</h1>
      <form onSubmit={handleFormSubmit}>
        <label htmlFor="email">Email:</label>
        <input type="email" id="email" name="email" required value={email} onChange={(e) => setEmail(e.target.value)} />

        <label htmlFor="password">Password:</label>
        <input type="password" id="password" name="password" required value={password} onChange={(e) => setPassword(e.target.value)} />

        <button type="submit">Sign Up</button>
      </form>

      <div className="form-switch">
        <span>Already have an account?</span>
        <Link to="/login">Log in</Link>
      </div>
    </div>
  );
}

export default Signup;
