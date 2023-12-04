import React, { useState, useRef, useContext } from "react";
import { UsernameContext } from "../context/UsernameContext";
import { Link } from "react-router-dom";
import { getPreference, updatePreference } from "../utils/client";


const ProfilePreference = () => {
    const { Username } = useContext(UsernameContext);
    const [ skills, setSkills ] = useState([]);
    const [ desiredJobs, setDesiredJobs ] = useState([]);
    const [ desiredLocations, setDesiredLocations ] = useState([]);

    const initUserPreference = async () => {
        const response = await getPreference(Username);
        setSkills(response.data.skills);
        setDesiredJobs(response.data.desiredJobsTitle);
        setDesiredLocations(response.data.desiredJobsLocation);
    }
    initUserPreference();

    const inputSkills = useRef(null);
    const inputDesiredJobs = useRef(null);
    const inputDesiredLocations = useRef(null);

    const [editMode, setEditMode] = useState(false);
    
    const startEditing = () => {
        setEditMode(true);
    }

    const saveEditing = async () => {
        try {
            console.log(inputSkills.current.value.split(','), inputDesiredJobs.current.value.split(','), inputDesiredLocations.current.value.split(','));
            const response = await updatePreference(Username, inputSkills.current.value.split(','), inputDesiredJobs.current.value.split(','), inputDesiredLocations.current.value.split(','));
            setEditMode(false);
        } catch (error) {
            alert("Error: Failed to update preference");
        }
    }

    const cancelEditing = () => {
        inputSkills.current.value = skills;
        inputDesiredJobs.current.value = desiredJobs;
        inputDesiredLocations.current.value = desiredLocations;
        setEditMode(false);
    }
    
    return (
        <>
            <div className="p-4 sm:ml-64">
                <div className="flex flex-col items-center justify-center px-6 py-8 mx-auto lg:py-0">
                <div className="w-full rounded-lg md:mt-0 sm:max-w-lg">
                <h1 className="text-xl font-bold text-gray-900 md:text-2xl">Preference</h1>

            <form onSubmit={e => { e.preventDefault(); }}>
                <div className="space-y-4 md:space-y-6">
                    <div>
                        <label className="block mb-1 text-lg font-medium text-gray-900">Skills</label>
                        <input name="username" 
                        className="bg-gray-200 text-gray-900 lg:text-lg rounded-lg w-full p-2.5 mb-5" 
                        defaultValue={skills} readOnly={!editMode} 
                        ref={inputSkills} />              
                    </div>
				</div>
                <div className="space-y-4 md:space-y-6">
                    <div>
                        <label className="block mb-1 text-lg font-medium text-gray-900">Desired Job Titles</label>
                        <input  
                        className="bg-gray-200 text-gray-900 lg:text-lg rounded-lg w-full p-2.5 mb-5" 
                        defaultValue={desiredJobs} readOnly={!editMode} 
                        ref={inputDesiredJobs} />              
                    </div>
				</div>
                <div className="space-y-4 md:space-y-6">
                    <div>
                        <label className="block mb-1 text-lg font-medium text-gray-900">Desired Job Locations</label>
                        <input 
                        className="bg-gray-200 text-gray-900 lg:text-lg rounded-lg w-full p-2.5 mb-5" 
                        defaultValue={desiredLocations} readOnly={!editMode} 
                        ref={inputDesiredLocations} />              
                    </div>
				</div>
                <div>
                    <div>
                        <label className="block mb-1 text-lg font-medium text-gray-900">Tracking Companies</label>
                        <Link to="/company_tracking">
                        <button className="bg-gray-200 text-gray-900 lg:text-lg rounded-lg w-full p-2.5 mb-5" />    
                        </Link>          
                    </div>
                </div>
                <div className="Buttons">
                    <button 
                    className="text-white bg-gray-500 hover:bg-gray-700 font-medium rounded-lg text-sm px-3 py-2 mb-2"
                    id="edit-button" onClick={() => startEditing()} style={{display: editMode ? "none" : "inline-block" }}>Edit</button>
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
        </>
    );
};
 
export default ProfilePreference;