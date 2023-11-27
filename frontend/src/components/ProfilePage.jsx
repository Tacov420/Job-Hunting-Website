import React, { useState } from "react";
import { Routes, Route, Link } from "react-router-dom";
import PersonalInfo from "./PersonalInfo";
import Preference from "./Preference";
 
const ProfilePage = () => {
    return (
        <>
            <h2>Profile</h2>
            <div className="tabs-container">
                <div className="tabs">
                    <span>
                        <Link to="/profile/personal_info">Personal Information</Link>
                    </span>
                    <span>
                        <Link to="/profile/preference">Preference</Link>
                    </span>
                </div>
            </div>
            <div className="tab-content">
                <Routes>
                    <Route path="/" element={<PersonalInfo />} />
                    <Route path="/personal_info" element={<PersonalInfo />} />
                    <Route path="/preference" element={<Preference />} />
                </Routes>
            </div>
        </>
    );
};
 
export default ProfilePage;