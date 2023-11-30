import React from "react";
import  TitleBar  from '../components/TitleBar';
import { MdEmail } from "react-icons/md";
import { Routes, Route, Link , useParams} from "react-router-dom";
import { RiDeleteBin5Fill } from "react-icons/ri";


const CompanyTracking = () => {
    const id='123';
    const linkName = 'company_tracking/'+ id;

    return (
        <>
        <TitleBar display={true} currentPage={'company tracking'}/>
        <div className='items-center mx-24 px-10 mt-6'>
            <div className="flex flex-wrap -mx-3 mb-5">
                <div className="w-full md:w-full px-3 mb-6 md:mb-0">
                    <div className="relative">
                        <div className="absolute inset-y-0 start-0 flex items-center ps-3 pointer-events-none">
                            <svg className="w-4 h-4 text-gray-500" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 20">
                                <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m19 19-4-4m0-7A7 7 0 1 1 1 8a7 7 0 0 1 14 0Z"/>
                            </svg>
                        </div>
                        <input type="search" id="default-search" 
                            className="!outline-none block w-full p-2 ps-10 text-lg text-gray-900 rounded-lg bg-gray-200" 
                            placeholder="Search Company..."/>
                        <button className="absolute end-1.5 bottom-1.5 items-center px-1.5 py-1 text-medium font-medium text-center text-white bg-blue-500 rounded-lg hover:bg-blue-400">
                            search
                        </button>
                    </div>
                </div>
            </div>
            
            <Link to={linkName}>
                <div className="relative md:w-5/6 p-4 my-4 bg-gray-100 border border-gray-200 rounded-lg shadow hover:bg-gray-200">   
                    <h5 className="mb-1 text-xl font-sm tracking-tight text-gray-900">company name</h5>                    
                    <MdEmail size={30} className='absolute end-1.5 bottom-4 items-center text-medium font-medium text-center text-gray-600'/> 
                </div>
                <button type="button" className="inline">
                    <RiDeleteBin5Fill size={25} className="text-red-600 hover:text-red-700"/>
                </button>

            </Link>
            <Link to={linkName}>
                <div className="relative md:w-5/6 p-4 my-4 bg-gray-100 border border-gray-200 rounded-lg shadow hover:bg-gray-200">   
                    <h5 className="mb-1 text-xl font-sm tracking-tight text-gray-900">company name</h5>                    
                    <MdEmail size={30} className='absolute end-1.5 bottom-4 items-center text-medium font-medium text-center text-green-500'/> 
                </div>
            </Link>
            
        </div>
        </>
    );
};
 
export default CompanyTracking;