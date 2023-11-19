import React, { useState, useRef } from 'react';
import axios from 'axios';

const LoginForm = ({ onCreateAccount, onPreferncce, onVerify }) => {
  const usernameRef = useRef(null);
  const passwordRef = useRef(null);
  const [error, setError] = useState(null);

  const handleLogin = async () => {
    const username = usernameRef.current.value;
    const password = passwordRef.current.value;
    try {
      const data = {
        userName: username,
        password: password,
      };
      const response = await axios.post('http://localhost:8000/api/login', data, {
        headers: {
          'Content-Type': 'application/json',
        },
      });
      
      if (response.status === 201) {
        console.log(response.data);
      } 
      setError(null);
    } catch (error) {
      if (error.response) {
        console.error('Login error:', error.response.data);
        console.error('Status code:', error.response.status);
        if (error.response.status == 403){
          alert(`Login failed: ${error.response.data}`);
        }
        else if (error.response.status == 400){
          if (error.response.data == "Hasn't verified"){
            onVerify()//跳到驗證信
          }
          else if (error.response.data == "Hasn't filled in preference"){
            onPreferncce();//跳到填preference
          }
        }
        else{

        }
        
      } else if (error.request) {
        console.error('No response received:', error.request);
        alert('Login failed: No response received.');
      } else {
        console.error('Error setting up the request:', error.message);
        alert(`Login failed: ${error.message}`);
      }
    
    }
  
  };

  return (
    <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
      <h2>Create an account or sign in</h2>
      <form style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
        <label htmlFor="username">Username:</label>
        <input
          type="text"
          id="username"
          name="username"
          ref={usernameRef}
        />

        <label htmlFor="password">Password:</label>
        <input
          type="password"
          id="password"
          name="password"
          ref={passwordRef}
        />

        <button type="button" onClick={handleLogin}>
          Login
        </button>
        <button type="button" onClick={onCreateAccount}>
          Create an account
        </button>

        {error && <p style={{ color: 'red' }}>{error}</p>}


      </form>
    </div>
  );
};

export default LoginForm;
