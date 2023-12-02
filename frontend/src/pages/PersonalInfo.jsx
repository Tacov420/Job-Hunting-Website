import React, { useState, useRef, useContext } from "react";
import { UsernameContext } from "../context/UsernameContext";
import { getPersonalInfo } from "../utils/client";

class UserInfo {
    constructor(username, emailAddr) {
        this.Username = username;
        this.EmailAddr = emailAddr;
    }
}

const PersonalInfo = () => {
    const { Username } = useContext(UsernameContext);
    const response = getPersonalInfo(Username);
    const info = new UserInfo(response.data);

    const newPassword = useRef(null);
    const confirmPassword = useRef(null);

    const [editMode, setEditMode] = useState(false);
    
    const startEditing = () => {
        setEditMode(true);
    }

    const saveEditing = async () => {
        if (newPassword.current?.value == "") {
            alert("Error: \"New Password\" is required");
        }
        else if (confirmPassword.current?.value == "") {
            alert("Error: \"Confirm Password\" is required");
        }
        else if (newPassword.current?.value !== confirmPassword.current?.value) {
            alert("Error: \"New Password\" and \"Confirm Password\" don't match.");
        }
        else {
            setEditMode(false);
        }
    }

    const cancelEditing = () => {
        newPassword.current.value = "";
        confirmPassword.current.value = "";
        setEditMode(false);
    }

    return (
        <div>
        <div className="p-4 sm:ml-64">
            <div className="flex flex-col items-center justify-center px-6 py-8 mx-auto lg:py-0">
            <div className="w-full rounded-lg md:mt-0 sm:max-w-lg">
            <h1 className="text-xl font-bold text-gray-900 md:text-2xl">Personal Information</h1>
            <form onSubmit={e => { e.preventDefault(); }}>
                <div className="space-y-4 md:space-y-6">
                    <div>
                        <label className="block mb-1 text-lg font-medium text-gray-900">Username</label>
                        <input name="username" 
                        className="bg-gray-200 text-gray-900 lg:text-lg rounded-lg w-full p-2.5 mb-5" 
                        value={info.Username} readOnly />              
                    </div>
				</div>
                <div className="space-y-4 md:space-y-6">
                    <div>
                        <label className="block mb-1 text-lg font-medium text-gray-900">Email Address</label>
                        <input type="text" name="email" id="email"
                        className="bg-gray-200 text-gray-900 lg:text-lg rounded-lg w-full p-2.5 mb-5" 
                        value={info.EmailAddr} readOnly />              
                    </div>
				</div>
                <div className="space-y-4 md:space-y-6">
                    <div>
                        <label className="block mb-1 text-lg font-medium text-gray-900">New Password</label>
                        <input type="password" name="password" id="password"
                        className="bg-gray-200 text-gray-900 lg:text-lg rounded-lg w-full p-2.5 mb-5" 
                        readOnly={!editMode}
                        ref={newPassword}/>              
                    </div>
				</div>
                <div className="space-y-4 md:space-y-6">
                    <div>
                        <label className="block mb-1 text-lg font-medium text-gray-900">Confirm Password</label>
                        <input type="password" name="confirm-password" id="confirm-password"
                        className="bg-gray-200 text-gray-900 lg:text-lg rounded-lg w-full p-2.5 mb-5" 
                        readOnly={!editMode}
                        ref={confirmPassword}/>              
                    </div>
				</div>
                
                <div className="Buttons">
                    <button id="edit-button" 
                        className="text-white bg-gray-500 hover:bg-gray-700 font-medium rounded-lg text-sm px-3 py-2 mb-2"
                        onClick={() => startEditing()} style={{display: editMode ? "none" : "inline-block" }}>Edit</button>
                    <button id="save-button" 
                        className="text-white bg-green-500 hover:bg-green-700 font-medium rounded-lg text-sm px-3 py-2 mb-2"
                        onClick={() => saveEditing()} style={{display: editMode ? "inline-block" : "none" }}>Save</button>
                    <button id="cancel-button" 
                        className="text-white bg-red-500 hover:bg-red-700 font-medium rounded-lg text-sm px-3 py-2 mb-2"
                        onClick={() => cancelEditing()} style={{display: editMode ? "inline-block" : "none" }}>Cancel</button>
                </div>
            </form>
        </div>
        </div>
        </div>
        </div>
    );
};
 
export default PersonalInfo;