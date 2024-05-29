import React, { useState, useRef, useContext } from "react";
import { UsernameContext } from "../context/UsernameContext";
import { getPreference, updatePreference } from "../utils/client";
import { VscSmiley } from "react-icons/vsc";


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
                <h1 className="text-xl font-bold text-gray-900 md:text-2xl py-4">
                    <VscSmiley className="inline mr-3" size={35}/>Preference
                </h1>

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
                <div className="Buttons">
                    <button 
                        className="px-7 bg-gray-500 rounded-md font-sm text-white hover:bg-gray-400"
                        id="edit-button" 
                        onClick={() => startEditing()} style={{display: editMode ? "none" : "inline-block" }}>Edit</button>
                    <button 
                        id="save-button" 
                        className="px-7 bg-green-300 rounded-md font-sm text-gray-800 hover:bg-green-400"
                        onClick={() => saveEditing()} style={{display: editMode ? "inline-block" : "none" }}>Save</button>
                    <button 
                        id="cancel-button" 
                        className="ml-1 px-7 bg-red-300 rounded-md font-sm text-gray-800 hover:bg-red-400"
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