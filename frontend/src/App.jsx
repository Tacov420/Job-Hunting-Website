import React from 'react';
import { Routes, Route } from "react-router-dom";
import TitleBar from './components/TitleBar';
import JobsPage from "./components/JobsPage";
import CompanyTracking from "./components/CompanyTracking";
import ProgressTracking from "./components/ProgressTracking";
import Notifications from "./components/Notifications";
import DiscussForum from "./components/DiscussForum";
import ProfileTabList from "./components/ProfileTabList";
 
const App = () => {
    // const tabData = [
    //     { label: "Personal Information" },
    //     { label: "Preferences" }
    // ];
 
    return (
        <div>
            <TitleBar />
            <h1>I'm a piece of shit.</h1>
            <Routes>
                <Route path="*" element={<JobsPage />} />
                <Route path="/jobs" element={<JobsPage />} />
                <Route path="/profile/*" element={<ProfileTabList />} />
                <Route path="/company_tracking" element={<CompanyTracking />} />
                <Route path="/progress_tracking" element={<ProgressTracking />} />
                <Route path="/discuss_forum" element={<DiscussForum />} />
                <Route path="/notifications" element={<Notifications />} />
            </Routes>
        </div>
    );
};
 
export default App;