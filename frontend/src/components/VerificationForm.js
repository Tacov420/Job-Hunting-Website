import React, { useContext, useState } from 'react';
import axios from 'axios';
import { UsernameContext } from '../context/UsernameContext';

const VerificationForm = ({ onContinue }) => {
  const [email, setEmail] = useState('');
  const [verificationCode, setVerificationCode] = useState('');
  
  const { Username } = useContext(UsernameContext);

  const handleContinue = async () => {
    try {
      console.log('username: ', Username)
      if (email !== '' && verificationCode !== '') {
        const data = {
          userName: Username,
          verificationCode: verificationCode,
        };
  
        const response = await axios.post('http://localhost:8000/api/register/verify', data);
        console.log(response.data);
        onContinue();
        
      } else {
        alert('Please fill in all fields correctly.');
      }
    } catch (error) {
      console.error('Verification failed:', error.response ? error.response.data : error.message);
      alert('Verification failed. Please try again.');
    }
  };
  
  const handleSendVerification = async () => {
    try {
      const data = {
        userName: Username,
        email: email,
      };
      //console.log('username: ', Username)
      const response = await axios.post('http://localhost:8000/api/register/sendVerification', data);
      console.log(response.data);
      alert('Sent verification code successfully.');
    } catch (error) {
      if (error.response) {
        if (error.response.status == 403){ 
          alert(`failed: ${error.response.data}`);
        }
      }
      //console.error('Sending verification code failed:', error.response ? error.response.data : error.message);
      //alert('Sending verification code failed. Please try again.');
    }


  };
  
  return (
    <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
      <h2>Account</h2>

      <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', marginBottom: '10px' }}>
        <label htmlFor="email">Email Address:</label>
        <div style={{ display: 'flex', alignItems: 'center', width: '100%' }}>
          <input type="email" id="email" name="email" value={email} onChange={(e) => setEmail(e.target.value)} />
        </div>
      </div>

      <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', marginBottom: '10px' }}>
        <label htmlFor="verificationCode">Verification Code:</label>
        <input
          type="text"
          id="verificationCode"
          name="verificationCode"
          value={verificationCode}
          onChange={(e) => setVerificationCode(e.target.value)}
        />
      </div>

      
      <button type="button" onClick={handleSendVerification}>
        Send Verification
      </button>
      

      <button type="button" onClick={handleContinue}>
        Continue
      </button>
    </div>
  );


};

export default VerificationForm;


