import React, { useState } from "react";
import { Link } from "react-router-dom";

class UserPreference {
    constructor(skills, desiredJobs, desiredLocations) {
        this.Skills = skills;
        this.DesiredJobs = desiredJobs;
        this.DesiredLocations = desiredLocations;
    }
}

const Preference = () => {
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
            <h3>Preference</h3>
            <form>
                <div>
                    <label htmlFor="skills">Skills</label>
                    <input type="text" name="skills" id="skills" defaultValue={preference.Skills} readOnly={!editMode} />
                </div>
                <div>
                    <label htmlFor="desired-job-titles">Desired Job Titles</label>
                    <input type="text" name="desired-job-titles" id="desired-job-titles" defaultValue={preference.DesiredJobs} readOnly={!editMode} />
                </div>
                <div>
                    <label htmlFor="desired-job-locations">Desired Job Locations</label>
                    <input type="text" name="desired-job-locations" id="desired-job-locations" defaultValue={preference.DesiredLocations} readOnly={!editMode} />
                </div>
                <div>
                    <span>
                        <Link to="/company_tracking">Tracking Companies</Link>
                    </span>
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
 
export default Preference;