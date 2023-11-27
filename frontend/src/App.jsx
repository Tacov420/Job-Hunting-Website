import { useState } from 'react'
import Login from './pages/Login';
import Register from './pages/Register';
import Verification from './pages/Verification';
import Preference from './pages/Preference';
import HomePage from './pages/Homepage';

import CompanyTracking from './pages/CompanyTracking';
import ProgressTracking from './pages/ProgressTracking';
import Notifications from './pages/Notifications';
import DiscussForum from './pages/DiscussForum';
import ProfilePage from './pages/ProfilePage';
import {Routes , Route} from "react-router-dom";


import './App.css'

const App = () => {
  
    return (
      <>
      <Routes>
          <Route strict path="/" element={<Login/>}/>
          <Route path="/register" element={<Register/>}/>
          <Route path="/verification" element={<Verification/>}/>
          <Route path="/preference" element={<Preference/>}/>
          <Route path="/home"  element={<HomePage/>}/>

          <Route path="/profile/*" element={<ProfilePage />} />
          <Route path="/company_tracking" element={<CompanyTracking />} />
          <Route path="/progress_tracking" element={<ProgressTracking />} />
          <Route path="/discuss_forum" element={<DiscussForum />} />
          <Route path="/notifications" element={<Notifications />} />
      </Routes>
      </>
    );
};

export default App
