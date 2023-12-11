import React, { useState } from "react";
import { Routes, Route, Link } from "react-router-dom";
import PersonalInfo from "../components/PersonalInfo"
import ProfilePreference from "../components/ProfilePreference";
import  TitleBar  from '../components/TitleBar';
import { BsFillPersonVcardFill } from "react-icons/bs";
import { VscSmiley } from "react-icons/vsc";
 
const ProfilePage = () => {
    return (
        <>
            <TitleBar display={true} currentPage={'profile'}/>
            <aside id="logo-sidebar" className="fixed top-12 left-24 ml-12 mt-10 z-40 w-64 h-screen transition-transform -translate-x-full bg-white border-r border-gray-200 sm:translate-x-0" aria-label="Sidebar">
                <div className="h-full px-3 pb-4 overflow-y-auto bg-white">
                    <h2 className="text-xl font-bold text-gray-500 px-16 py-3">Profile</h2>
                    <ul className="space-y-2 font-medium">
                        <li>
                            <Link to="/profile/personal_info">
                                <div className="flex items-center p-2 text-green-800 rounded-lg hover:bg-green-50">
                                <BsFillPersonVcardFill size={30} className="text-green-800"/>
                                <span id="toPersonalInfo" className="ms-3">
                                    Personal Information
                                </span>
                                </div>
                            </Link>
                        </li>
                        <li>
                            <Link to="/profile/profilepreference">
                                <div className="flex items-center p-2 text-green-800 rounded-lg hover:bg-green-50">
                                <VscSmiley size={30} className="text-green-800"/>
                                <span id="toPreference" className="ms-3">
                                    Preference
                                </span>
                                </div>
                            </Link>
                        </li>
                    </ul>
                </div>
            </aside>
            <div className="tab-content">
                <Routes>
                    <Route path="/" element={<PersonalInfo />} />
                    <Route path="/personal_info" element={<PersonalInfo />} />
                    <Route path="/profilepreference" element={<ProfilePreference />} />
                </Routes>
            </div>
        </>
    );
};
 
export default ProfilePage;