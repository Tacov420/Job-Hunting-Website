import React, { useState } from "react";
import { Routes, Route, Link } from "react-router-dom";
import PersonalInfoPage from "./PersonalInfoPage";
import PreferencePage from "./PreferencePage";
 
const ProfilePage = () => {
    const [editMode, setEditMode] = useState(false);
    
    const startEditing = () => {
        setEditMode(true);
    }

    const saveEditing = () => {
        setEditMode(false);
    }

    const cancelEditing = () => {
        setEditMode(false);
    }

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
                    <Route path="/" element={<PersonalInfoPage />} />
                    <Route path="/personal_info" element={<PersonalInfoPage />} />
                    <Route path="/preference" element={<PreferencePage />} />
                </Routes>
            </div>
            <div className="Buttons">
                <button id="edit-button" onClick={() => startEditing()} style={{display: editMode ? "none" : "inline-block" }}>Edit</button>
                <button id="save-button" onClick={() => saveEditing()} style={{display: editMode ? "inline-block" : "none" }}>Save</button>
                <button id="cancel-button" onClick={() => cancelEditing()} style={{display: editMode ? "inline-block" : "none" }}>Cancel</button>
            </div>
        </>
    );
};
 
export default ProfilePage;