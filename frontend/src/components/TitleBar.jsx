import React from "react";
import { IoPersonCircle } from "react-icons/io5";
import { FaBell } from "react-icons/fa";
import { Routes, Route, Link } from "react-router-dom";

const TitleBar = () => {
    return (
        <>
        <nav class="bg-white border-gray-200">
            <div class="max-w-screen-xl flex flex-wrap items-center justify-between mx-auto p-4">
                <span class="self-center text-2xl font-bold whitespace-nowrap text-blue-700">Job Hunting</span>
            <div class="flex items-center md:order-2 space-x-3 md:space-x-0 rtl:space-x-reverse">
                <Link to="/profile">
                <button type="button" class="flex text-sm bg-gray-200 rounded-full mx-3">
                    <span class="sr-only">Open user menu</span>
                    <IoPersonCircle size={30}/>
                </button>
                </Link>
                <button type="button" class="flex text-sm bg-gray-200 rounded-full mx-3">
                    <span class="sr-only">Open user menu</span>
                    <FaBell size={25}/>
                </button>
            </div>
            
            <div class="items-center justify-between hidden w-full md:flex md:w-auto md:order-1" id="navbar-user">
                <ul class="flex flex-col font-medium p-4 md:p-0 mt-4 border border-gray-100 rounded-lg bg-gray-50 md:space-x-8 rtl:space-x-reverse md:flex-row md:mt-0 md:border-0 md:bg-white 0">
                <li>
                    <Link to="/home">
                    <span class="block py-2 px-3 text-white bg-blue-700 rounded md:bg-transparent md:text-blue-700 md:p-0" aria-current="page">Jobs</span>
                    </Link>
                </li>
                <li>
                    <Link to="/discuss_forum">
                    <span class="block py-2 px-3 text-gray-900 rounded hover:bg-gray-100 md:hover:bg-transparent md:hover:text-blue-700 md:p-0">Discuss Forum</span>
                    </Link>
                </li>
                <li>
                    <Link to="/company_tracking">
                    <span class="block py-2 px-3 text-gray-900 rounded hover:bg-gray-100 md:hover:bg-transparent md:hover:text-blue-700 md:p-0">Company Tracking</span>
                    </Link>
                </li>
                <li>
                    <Link to="/progress_tracking">
                    <span class="block py-2 px-3 text-gray-900 rounded hover:bg-gray-100 md:hover:bg-transparent md:hover:text-blue-700 md:p-0">Progress Tracking</span>
                    </Link>
                </li>
                </ul>
            </div>
            </div>
        </nav>
        </>
    );
};
 
export default TitleBar;