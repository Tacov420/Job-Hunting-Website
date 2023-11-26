import React, { useState } from 'react';
import './App.css';
import LoginForm from './pages/LoginForm';
import RegisterForm from './pages/RegisterForm';
import VerificationForm from './pages/VerificationForm';
import PreferenceForm from './pages/PreferenceForm';
import HomePage from './pages/HomePage';
import {Routes , Route} from "react-router-dom";


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

  const gotoHome = () => {
    setCurrentPage('home')
  }

  return (
    <>
    <Routes>
        <Route strict path="/" element={<LoginForm onLogin={gotoHome} onCreateAccount={handleCreateAccount} onVerify={gotoVerify} onPreferncce={gotoPreference} />}/>
        <Route path="/register" element={<RegisterForm onVerify={gotoVerify} />} />
        <Route path="/verification" element={<VerificationForm onContinue={gotoPreference} />} />
        <Route path="/preference" element={<PreferenceForm onContinue={gotoHome} />} />
        <Route path="/home" element={<HomePage/>} />
    </Routes>
    
    {/*<div>
      {currentPage === 'login' && <LoginForm onLogin={gotoHome} onCreateAccount={handleCreateAccount} onVerify={gotoVerify} onPreferncce={gotoPreference} />}
      {currentPage === 'register' && <RegisterForm onVerify={gotoVerify} />}
      {currentPage === 'verification' && <VerificationForm onContinue={gotoPreference} />}
      {currentPage === 'preference' && <PreferenceForm onContinue={gotoHome} />}
      {currentPage === 'home' && <HomePage/>}
    </div>*/}
    </>
  );
};

export default App;
