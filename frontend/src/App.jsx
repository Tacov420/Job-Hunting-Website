import { useState } from 'react'
import Login from './pages/Login';
import Register from './pages/Register';
import Verification from './pages/Verification';
import Preference from './pages/Preference';
import HomePage from './pages/Homepage';
import {Routes , Route} from "react-router-dom";

//import './App.css'

const App = () => {
  
    return (
      <>
      <Routes>
          <Route strict path="/" element={<Login/>}/>
          <Route path="/register" element={<Register/>}/>
          <Route path="/verification" element={<Verification/>}/>
          <Route path="/preference" element={<Preference/>}/>
          <Route path="/home"  element={<HomePage/>}/>
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

export default App
