import React, { useState } from "react";
import { Link } from "react-router-dom";

class UserPreference {
    constructor(skills, desiredJobs, desiredLocations) {
        this.Skills = skills;
        this.DesiredJobs = desiredJobs;
        this.DesiredLocations = desiredLocations;
    }
}

const Profilepreference = () => {
    const preference = new UserPreference(["fries", "hamburgers"], ["cashier", "chef"], ["Taipei", "Taichung"]);

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
            <div class="p-4 sm:ml-64">
                <div className="flex flex-col items-center justify-center px-6 py-8 mx-auto lg:py-0">
                <div className="w-full rounded-lg md:mt-0 sm:max-w-lg">
                <h1 className="text-xl font-bold text-gray-900 md:text-2xl">Preference</h1>

            <form>
                <div className="space-y-4 md:space-y-6">
                    <div>
                        <label className="block mb-1 text-lg font-medium text-gray-900">Skills</label>
                        <input name="username" 
                        className="bg-gray-200 text-gray-900 lg:text-lg rounded-lg w-full p-2.5 mb-5" 
                        defaultValue={preference.Skills} readOnly={!editMode} />              
                    </div>
				</div>
                <div className="space-y-4 md:space-y-6">
                    <div>
                        <label className="block mb-1 text-lg font-medium text-gray-900">Desired Job Titles</label>
                        <input  
                        className="bg-gray-200 text-gray-900 lg:text-lg rounded-lg w-full p-2.5 mb-5" 
                        defaultValue={preference.DesiredJobs} readOnly={!editMode} />              
                    </div>
				</div>
                <div className="space-y-4 md:space-y-6">
                    <div>
                        <label className="block mb-1 text-lg font-medium text-gray-900">Desired Job Locations</label>
                        <input 
                        className="bg-gray-200 text-gray-900 lg:text-lg rounded-lg w-full p-2.5 mb-5" 
                        defaultValue={preference.DesiredLocations} readOnly={!editMode} />              
                    </div>
				</div>
                <div>
                    <span>
                        <Link to="/company_tracking">Tracking Companies</Link>
                    </span>
                </div>
                <div className="Buttons">
                    <button id="save-button" 
                    class="text-white bg-green-500 hover:bg-green-700 font-medium rounded-lg text-sm px-3 py-2 mb-2"

                    onClick={() => saveEditing()} style={{display: editMode ? "inline-block" : "none" }}>Save</button>
                    <button id="cancel-button" 
                    class="text-white bg-red-500 hover:bg-red-700 font-medium rounded-lg text-sm px-3 py-2 mb-2"

                    onClick={() => cancelEditing()} style={{display: editMode ? "inline-block" : "none" }}>Cancel</button>
                </div>
            </form>
            <button 
                class="text-white bg-gray-500 hover:bg-gray-700 font-medium rounded-lg text-sm px-3 py-2 mb-2"
                id="edit-button" onClick={() => startEditing()} style={{display: editMode ? "none" : "inline-block" }}>Edit</button>
            </div>
            </div>
            </div>
        </>
    );
};
 
export default Profilepreference;