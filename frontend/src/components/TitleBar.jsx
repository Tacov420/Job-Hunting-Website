import React from "react";
import { IoPersonCircle } from "react-icons/io5";
import { FaBell } from "react-icons/fa";
import { Routes, Route, Link , useParams} from "react-router-dom";
import { TbLogout2 } from "react-icons/tb";

const TitleBar = ({display , currentPage}) => {
    //const dis
    //const currentPage = currentPage;
   
    return (
        <>
        {display && (
        <nav className="bg-white border-gray-200">
            <div className="max-w-screen-xl flex flex-wrap items-center justify-between mx-auto p-4">
                <span className="self-center text-2xl font-bold whitespace-nowrap text-blue-700">Job Hunting</span>
            <div className="flex items-center md:order-2 space-x-3 md:space-x-0 rtl:space-x-reverse">
                <Link to="/profile">
                <button type="button" className="flex text-sm bg-gray-200 hover:bg-gray-300 rounded-full mx-3">
                    <span className="sr-only">Go profile page</span>
                    <IoPersonCircle size={30}/>
                </button>
                </Link>
                <Link to="/notifications">
                <button type="button" className="flex text-sm bg-gray-200 hover:bg-gray-300 rounded-full mx-3">
                    <span className="sr-only">Go notification page</span>
                    <FaBell size={25}/>
                </button>
                </Link>
                <Link to="/">
                <button type="button" className="flex text-sm bg-gray-200 hover:bg-gray-300 rounded-full mx-3">
                    <TbLogout2 size={30}/>
                </button>
                </Link>
            </div>
            
            <div className="items-center justify-between hidden w-full md:flex md:w-auto md:order-1" id="navbar-user">
                <ul className="flex flex-col font-medium p-4 md:p-0 mt-4 border border-gray-100 rounded-lg bg-gray-50 md:space-x-8 rtl:space-x-reverse md:flex-row md:mt-0 md:border-0 md:bg-white 0">
                    <li>
                        <Link to="/home">
                        <span className={`${currentPage=='home' && "font-extrabold"} block py-2 px-3 text-gray-900 rounded hover:bg-gray-100 md:hover:bg-transparent md:hover:text-blue-700 md:p-0`}>Jobs</span>
                        </Link>
                    </li>
                    <li>
                        <Link to="/discuss_forum">
                        <span className={`${currentPage=='discuss forum' && "font-extrabold"} block py-2 px-3 text-gray-900 rounded hover:bg-gray-100 md:hover:bg-transparent md:hover:text-blue-700 md:p-0`}>Discuss Forum</span>
                        </Link>
                    </li>
                    <li>
                        <Link to="/company_tracking">
                        <span className={`${currentPage=='company tracking' && "font-extrabold"} block py-2 px-3 text-gray-900 rounded hover:bg-gray-100 md:hover:bg-transparent md:hover:text-blue-700 md:p-0`}>Company Tracking</span>
                        </Link>
                    </li>
                    <li>
                        <Link to="/progress_tracking">
                        <span className={`${currentPage=='progress tracking' && "font-extrabold"} block py-2 px-3 text-gray-900 rounded hover:bg-gray-100 md:hover:bg-transparent md:hover:text-blue-700 md:p-0`}>Progress Tracking</span>
                        </Link>
                    </li>
                </ul>
            </div>
            </div>
        </nav>
        )}
        </>
    );
};
 
export default TitleBar;