import React from "react";
import { Routes, Route, Link } from "react-router-dom";

const TitleBar = () => {
    return (
        <>
            <div className="tabs-container">
                <div className="tabs">
                    <span>
                        <Link to="/jobs">Jobs</Link>
                    </span>
                    <span>
                        <Link to="/discuss_forum">Discuss Forum</Link>
                    </span>
                    <span>
                        <Link to="/company_tracking">Company Tracking</Link>
                    </span>
                    <span>
                        <Link to="/progress_tracking">Progress Tracking</Link>
                    </span>
                    <span>
                        <Link to="/notifications">Notifications</Link>
                    </span>
                    <span>
                        <Link to="/profile">Profile</Link>
                    </span>
                </div>
            </div>
        </>
    );
};
 
export default TitleBar;