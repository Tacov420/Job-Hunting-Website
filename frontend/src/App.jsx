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
import AddPost from './pages/AddPost';
import ViewPost from './pages/ViewPost';
import EditPost from './pages/EditPost';
import PostItem from './components/Post'; 
import ReplyItem from './components/Reply'; 

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
          <Route strict path="/discuss_forum/*" element={<DiscussForum />} />
          <Route path="/discuss_forum/add" element={<AddPost />} />
          <Route path="/discuss_forum/edit/:post_id" element={<EditPost />} />
          <Route path="/discuss_forum/view/:post_id" element={<ViewPost />} />
          <Route path="/notifications" element={<Notifications />} />
      </Routes>
      <PostItem />
      <ReplyItem />

      </>
    );
};

export default App
