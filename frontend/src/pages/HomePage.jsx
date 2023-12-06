import React, { useContext, useState, useRef, useEffect } from 'react';
import { UsernameContext } from '../context/UsernameContext';
import { Link , useNavigate } from "react-router-dom";
import  TitleBar  from '../components/TitleBar';
import SearchItem from '../components/SearchItem';
import { BiChevronDown } from "react-icons/bi";
import { AiOutlineSearch } from "react-icons/ai";


const HomePage = () => {
    const { Username, updateUsername } = useContext(UsernameContext);
	const navigate = useNavigate();

	useEffect(() => {
		console.log('Username changed:', Username);
	}, [Username]);

  

    return (
        <>
        <TitleBar display={true} currentPage={'home'}/>
        <div className="max-w-screen-lg mx-auto">
            <div className="md:col-span-8 pt-5">
                <div className="flex flex-wrap -mx-3 mb-5">
                    <div className="w-full md:w-3/5 px-3 mb-6 md:mb-0">
                        <div className="relative">
                            <div className="absolute inset-y-0 start-0 flex items-center ps-3 pointer-events-none">
                                <svg className="w-4 h-4 text-gray-500" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 20">
                                    <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m19 19-4-4m0-7A7 7 0 1 1 1 8a7 7 0 0 1 14 0Z"/>
                                </svg>
                            </div>
                            <input type="search" id="default-search" 
                                className="block w-full p-2 ps-10 text-lg text-gray-900 rounded-lg bg-gray-200" 
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
                        <select id="small" class="block w-full p-3 ps-10 text-lg text-gray-500 rounded-lg bg-gray-200">
                            <option selected disabled>Level...</option>
                            <option 
                                value="0"
                                key='level0'
                                className="py-4 text-lg bg-gray-100 text-gray-500 hover:bg-gray-600 hover:text-white"
                                >level 0 : Entry-level
                            </option>
                            <option 
                                value="1"
                                key='level0'
                                className="py-4 text-lg bg-gray-100 text-gray-500 hover:bg-gray-600 hover:text-white"
                                >level 1 : Mid-senior level
                            </option>       
                        </select>
                        </div>
                    </div>
                </div>
                <div className="flex flex-wrap -mx-3 mb-2">
                    <div className="w-full px-3 mb-6 md:mb-0">
                        <div className="relative">
                            <div className="absolute inset-y-0 start-0 flex items-center ps-3 pointer-events-none">
                                <svg className="w-4 h-4 text-gray-500" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 20">
                                    <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m19 19-4-4m0-7A7 7 0 1 1 1 8a7 7 0 0 1 14 0Z"/>
                                </svg>
                            </div>
                            <input type="search" id="default-search" 
                                className="block w-full p-2 ps-10 text-lg text-gray-900 rounded-lg bg-gray-200" 
                                placeholder="Company..."/>
        
                        </div>
                    </div>
                </div>
                <button type="submit" 
                    className="text-white bg-gray-500 hover:bg-gray-700 font-medium rounded-lg text-medium px-5 py-1 mb-2 mt-1"
                >Search</button>

                <div className="w-full">
                    <div className="relative flex py-4 items-center text-lg">
                        <div className="flex-grow border-t-2 border-gray-300"></div>
                        <span className="flex-shrink mx-4 text-gray-400">Search Result</span>
                        <div className="flex-grow border-t-2 border-gray-300"></div>
                    </div>
                    <div className="grid grid-cols-2 gap-3">
                    <SearchItem id={'123'} companyName={'company1'} jobTitle={'sleep'} location={'NTU'} isTrack={true}/>
                    <SearchItem id={'456'} companyName={'company2'} jobTitle={'sleep'} location={'CSIE'} isTrack={false}/>
                    </div>
                </div>
            </div>  
        </div>


        </>
    );
};

export default HomePage;
