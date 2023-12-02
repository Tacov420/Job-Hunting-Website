import React, { useContext, useState, useRef, useEffect } from 'react';
import { UsernameContext } from '../context/UsernameContext';
import { getUser } from "../utils/client";
import { Link , useNavigate } from "react-router-dom";
import  TitleBar  from '../components/TitleBar';
import { FaRegBookmark } from "react-icons/fa";

const HomePage = () => {
    const { Username, updateUsername } = useContext(UsernameContext);
	const navigate = useNavigate()

	useEffect(() => {
		console.log('Username changed:', Username);
	}, [Username]);

  

    return (
        <>
        <TitleBar display={true} currentPage={'home'}/>
        <div className="max-w-screen-lg mx-auto">
            <form className="md:col-span-8 pt-5">
                <div className="flex flex-wrap -mx-3 mb-5">
                    <div className="w-full md:w-3/5 px-3 mb-6 md:mb-0">
                        <div className="relative">
                            <div className="absolute inset-y-0 start-0 flex items-center ps-3 pointer-events-none">
                                <svg className="w-4 h-4 text-gray-500" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 20">
                                    <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m19 19-4-4m0-7A7 7 0 1 1 1 8a7 7 0 0 1 14 0Z"/>
                                </svg>
                            </div>
                            <input type="search" id="default-search" 
                                className="block w-full p-3 ps-10 text-lg text-gray-900 rounded-lg bg-gray-200" 
                                placeholder="Search Job's Title..."/>
                        </div>
                    </div>

                    <div className="w-full md:w-2/5 px-3 mb-6 md:mb-0">
                        <div className="relative">
                            <div className="absolute inset-y-0 start-0 flex items-center ps-3 pointer-events-none">
                                <svg className="w-4 h-4 text-gray-500" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 20">
                                    <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m19 19-4-4m0-7A7 7 0 1 1 1 8a7 7 0 0 1 14 0Z"/>
                                </svg>
                            </div>
                            <input type="search" id="default-search" 
                                className="block w-full p-3 ps-10 text-lg text-gray-900 rounded-lg bg-gray-200" 
                                placeholder="Location..."/>
                           
                        </div>
                    </div>
                </div>
                <div className="flex flex-wrap -mx-3 mb-2">
                    <div className="w-full md:w-3/5 px-3 mb-6 md:mb-0">
                        <div className="relative">
                            <div className="absolute inset-y-0 start-0 flex items-center ps-3 pointer-events-none">
                                <svg className="w-4 h-4 text-gray-500" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 20">
                                    <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m19 19-4-4m0-7A7 7 0 1 1 1 8a7 7 0 0 1 14 0Z"/>
                                </svg>
                            </div>
                            <input type="search" id="default-search" 
                                className="block w-full p-3 ps-10 text-lg text-gray-900 rounded-lg bg-gray-200" 
                                placeholder="Search Related Skills..."/>
                          
                        </div>
                    </div>

                    <div className="w-full md:w-2/5 px-3 mb-6 md:mb-0">
                        <div className="relative">
                            <div className="absolute inset-y-0 start-0 flex items-center ps-3 pointer-events-none">
                                <svg className="w-4 h-4 text-gray-500" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 20">
                                    <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m19 19-4-4m0-7A7 7 0 1 1 1 8a7 7 0 0 1 14 0Z"/>
                                </svg>
                            </div>
                            <input type="search" id="default-search" 
                                className="block w-full p-3 ps-10 text-lg text-gray-900 rounded-lg bg-gray-200" 
                                placeholder="Company..."/>
        
                        </div>
                    </div>
                </div>
                <button type="submit" 
                    className="text-white bg-gray-500 hover:bg-gray-700 font-medium rounded-lg text-sm px-3 py-2 mb-2"
                >Search</button>

                <div className="w-full">
                    <p className="text-gray-700 text-2xl font-bold mb-3">
                        Recommendation
                    </p>
                    
                    <div className="relative w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 mb-3 leading-tight focus:outline-none focus:bg-white focus:border-gray-500">
                        <h5 className="mb-2 text-lg font-semibold text-gray-900">Company</h5>
                        <h5 className="mb-2 text-2xl font-semibold text-gray-900">Job's Title</h5>
                        
                        <p className="mb-3 font-normal text-gray-500">location</p>
                        <a href="#" className="inline-flex items-center text-blue-600 hover:underline">
                            Read more
                            <svg className="w-3 h-3 ms-2.5 rtl:rotate-[270deg]" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 18 18">
                                <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M15 11v4.833A1.166 1.166 0 0 1 13.833 17H2.167A1.167 1.167 0 0 1 1 15.833V4.167A1.166 1.166 0 0 1 2.167 3h4.618m4.447-2H17v5.768M9.111 8.889l7.778-7.778"/>
                            </svg>
                        </a>
                        <button type="submit" 
                                className="text-gray-600 absolute end-3 bottom-12 hover:text-gray-900 font-medium rounded-lg text-sm px-3 py-2"
                                
                        ><FaRegBookmark size={25}/></button>
                        
                    </div>
    
                </div>
            </form>  
        </div>


        </>
    );
};

export default HomePage;
