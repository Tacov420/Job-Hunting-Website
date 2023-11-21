import React, { useContext, useState, useEffect } from 'react';
import axios from 'axios';
import { UsernameContext } from '../context/UsernameContext';


const RegisterForm = ({ onVerify }) => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [checkColor, setCheckColor] = useState('black');
  const backendApiUrl = process.env.REACT_APP_BACKEND_API_URL;
  const { Username, updateUsername } = useContext(UsernameContext);
  
  useEffect(() => {
    console.log('Username changed:', Username);
  }, [Username]);

  const handleConfirmPasswordChange = (event) => {
    const newPassword = event.target.value;

    if (newPassword === password) {
      setCheckColor('green');
    } else if (newPassword !== '' && newPassword !== password) {
      setCheckColor('red');
    } else {
      setCheckColor('black');
    }

    setConfirmPassword(newPassword);
  };


  const handleContinue = async () => {
    try {
      if (username !== '' && password !== '' && confirmPassword !== '' && confirmPassword === password) {
        const data = {
          userName: username,
          password: password,
        };
        const response = await axios.post(backendApiUrl+'/register/personalInfo', data);
        console.log(response.data);
        if (response.status === 201) {
          updateUsername(username);
          onVerify();
        }
        else {
          alert(response.data)
        }
      } else {
        alert('Please fill in all fields correctly.');
      }
    } catch (error) {
      if (error.response) {
        if (error.response.status === 403){ //Username has already been registered
          alert(`failed: ${error.response.data}`);
        }
      }
    }
  };

  return (

    <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
      <h2>Account</h2>
      <label htmlFor="username">Username:</label>
      <input
      type="text"
      id="username"
      value={username}
      onChange={(e) => {
        setUsername(e.target.value); 
      }}
      />

      <label htmlFor="password">Password:</label>
      <input 
        type="password" 
        id="password" 
        name="password" 
        value={password} 
        onChange={(e) => setPassword(e.target.value)} 
      />

      <label htmlFor="confirmPassword">Confirm Password:</label>
      <input
        type="password"
        id="confirmPassword"
        name="confirmPassword"
        value={confirmPassword}
        onChange={handleConfirmPasswordChange}
      />
      <span style={{ color: checkColor }}>✔</span>

      <button type="button" onClick={handleContinue}>
        Continue
      </button>
    </div>
  

  );
};

export default RegisterForm;

