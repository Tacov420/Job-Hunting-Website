import React, { useState } from 'react';
import './App.css';
import LoginForm from './components/LoginForm';
import RegisterForm from './components/RegisterForm';
import VerificationForm from './components/VerificationForm';
import PreferenceForm from './components/PreferenceForm';
const App = () => {
  const [currentPage, setCurrentPage] = useState('login'); 

  const handleCreateAccount = () => {
    setCurrentPage('register'); 
  };

  const gotoVerify = () => {
    setCurrentPage('verification'); 
  };

  const gotoPreference = () => {
    setCurrentPage('preference'); 
  };

  return (
    <div>
      {currentPage === 'login' && <LoginForm onCreateAccount={handleCreateAccount} onVerify={gotoVerify} onPreferncce={gotoPreference} />}
      {currentPage === 'register' && <RegisterForm onVerify={gotoVerify} />}
      {currentPage === 'verification' && <VerificationForm onContinue={gotoPreference} />}
      {currentPage === 'preference' && <PreferenceForm />}
    </div>
  );
};

export default App;
