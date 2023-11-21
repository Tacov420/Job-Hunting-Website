import React, { useContext, useState, useRef, useEffect } from 'react';
import { UsernameContext } from '../context/UsernameContext';

import axios from 'axios';

const LoginForm = ({ onCreateAccount, onPreferncce, onVerify, onLogin }) => {
  const usernameRef = useRef(null);
  const passwordRef = useRef(null);
  const backendApiUrl = process.env.REACT_APP_BACKEND_API_URL;
  const { Username, updateUsername } = useContext(UsernameContext);

  useEffect(() => {
    console.log('Username changed:', Username);
  }, [Username]);

  const handleLogin = async () => {
    const username = usernameRef.current.value;
    const password = passwordRef.current.value;
    if (username ==='' || password === ''){
      alert('Please fill in all fields correctly.');
      return;
    }
    try {
      const data = {
        userName: username,
        password: password,
      };
      updateUsername(username);
      const response = await axios.post(backendApiUrl+'/login', data, {
        headers: {
          'Content-Type': 'application/json',
        },
      });
      
      if (response.status === 201) {
        console.log(response.data);
        onLogin();
      } 
      
    } catch (error) {
      if (error.response) {
        console.error('Login error:', error.response.data);
        console.error('Status code:', error.response.status);
        if (error.response.status === 403){
          alert(`Login failed: ${error.response.data}`);
        }
        else if (error.response.status === 400){
          if (error.response.data === "Hasn't verified"){
            onVerify()//跳到驗證信
          }
          else if (error.response.data === "Hasn't filled in preference"){
            onPreferncce();//跳到填preference
          }
        }
        else{
          //error.response.status === 500
        }
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

      </form>
    </div>
  );
};

export default LoginForm;
