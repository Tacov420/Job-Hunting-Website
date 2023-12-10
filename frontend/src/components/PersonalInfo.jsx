import React, { useState, useRef, useContext } from "react";
import { UsernameContext } from "../context/UsernameContext";
import { getPersonalInfo, updatePassword } from "../utils/client";
import { BsFillPersonVcardFill } from "react-icons/bs";


const PersonalInfo = () => {    
    const { Username } = useContext(UsernameContext);
    const [ infoUsername, setInfoUsername ] = useState("");
    const [ infoEmailAddr, setInfoEmailAddr ] = useState(""); 

    const initUserInfo = async (userName) => {
        const response = await getPersonalInfo(userName);
        setInfoUsername(response.data.userName);
        setInfoEmailAddr(response.data.email);
    }
    initUserInfo(Username);

    const newPassword = useRef(null);
    const confirmPassword = useRef(null);

    const [editMode, setEditMode] = useState(false);
    
    const startEditing = () => {
        setEditMode(true);
    }

    const saveEditing = async () => {
        if (newPassword.current?.value === "") {
            alert("Error: Field \"New Password\" is required");
        }
        else if (confirmPassword.current?.value === "") {
            alert("Error: Field \"Confirm Password\" is required");
        }
        else if (newPassword.current?.value !== confirmPassword.current?.value) {
            alert("Error: Fields \"New Password\" and \"Confirm Password\" don't match.");
        }
        else {
            try {
                const response = await updatePassword(Username, newPassword.current.value);
                newPassword.current.value = "";
                confirmPassword.current.value = "";
                setEditMode(false);
            } catch (error) {
                alert("Error: Failed to update password");
            }
        }
    }

    const cancelEditing = () => {
        newPassword.current.value = "";
        confirmPassword.current.value = "";
        setEditMode(false);
    }

    return (
        <div className="p-4 sm:ml-64">
            <div className="flex flex-col items-center justify-center mx-auto lg:py-0">
            <div className="w-full rounded-lg md:mt-0 sm:max-w-lg">
            <h1 className="text-xl font-bold text-gray-900 md:text-2xl py-4">
                <BsFillPersonVcardFill className="inline mr-3" size={35}/>Personal Information
            </h1>
            <form onSubmit={e => { e.preventDefault(); }}>
                <div className="space-y-4 md:space-y-6">
                    <div>
                        <label className="block mb-1 text-lg font-medium text-gray-900">Username</label>
                        <input name="username" 
                        id="username"
                        className="bg-gray-200 text-gray-900 lg:text-lg rounded-lg w-full p-2.5 mb-5" 
                        value={infoUsername} readOnly />              
                    </div>
				</div>
                <div className="space-y-4 md:space-y-6">
                    <div>
                        <label className="block mb-1 text-lg font-medium text-gray-900">Email Address</label>
                        <input type="text" name="email" id="email"
                        className="bg-gray-200 text-gray-900 lg:text-lg rounded-lg w-full p-2.5 mb-5" 
                        value={infoEmailAddr} readOnly />              
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
                        className="px-7 bg-gray-500 rounded-md font-sm text-white hover:bg-gray-400"
                        onClick={() => startEditing()} style={{display: editMode ? "none" : "inline-block" }}>Edit</button>
                    <button id="save-button" 
                        className="px-7 bg-green-300 rounded-md font-sm text-gray-800 hover:bg-green-400"
                        onClick={() => saveEditing()} style={{display: editMode ? "inline-block" : "none" }}>Save</button>
                    <button id="cancel-button" 
                        className="ml-1 px-7 bg-red-300 rounded-md font-sm text-gray-800 hover:bg-red-400"
                        onClick={() => cancelEditing()} style={{display: editMode ? "inline-block" : "none" }}>Cancel</button>
                </div>
            </form>
        </div>
        </div>
        </div>
       
    );
};
 
export default PersonalInfo;