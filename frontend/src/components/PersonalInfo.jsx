import React, { useState } from "react";

class UserInfo {
    constructor(username, emailAddr) {
        this.Username = username;
        this.EmailAddr = emailAddr;
    }
}

const PersonalInfo = () => {
    const info = new UserInfo("Test User", "Test Email");

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
            <h2>Personal Information</h2>
            <form>
                <div>
                    <label htmlFor="username">Username</label>
                    <input type="text" name="username" id="username" value={info.Username} readOnly />
                </div>
                <div>
                    <label htmlFor="email">Email Address</label>
                    <input type="text" name="email" id="email" value={info.EmailAddr} readOnly />
                </div>
                <div>
                    <label htmlFor="password">Password</label>
                    <input type="password" name="password" id="password" readOnly={!editMode} />
                </div>
                <div>
                    <label htmlFor="confirm-password">Confirm Password</label>
                    <input type="password" name="confirm-password" id="confirm-password" readOnly={!editMode} />
                </div>
                <div className="Buttons">
                    <button id="save-button" onClick={() => saveEditing()} style={{display: editMode ? "inline-block" : "none" }}>Save</button>
                    <button id="cancel-button" onClick={() => cancelEditing()} style={{display: editMode ? "inline-block" : "none" }}>Cancel</button>
                </div>
            </form>
            <button id="edit-button" onClick={() => startEditing()} style={{display: editMode ? "none" : "inline-block" }}>Edit</button>
        </>
    );
};
 
export default PersonalInfo;