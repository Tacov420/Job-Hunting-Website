import React, { useState, useContext } from "react";
import { IoBookmarks } from "react-icons/io5";
import { IoBookmarksOutline } from "react-icons/io5";
import {addCompany} from '../utils/client';
import { UsernameContext } from '../context/UsernameContext';

const SearchItem = ({id , companyName , jobTitle, level, isTrack}) => {
    const [trackStatus, setTrackStatus] = useState(isTrack);
    const [loading, setLoading] = useState(false);
    const { Username } = useContext(UsernameContext);

    const handleClick = async()=>{     
        setLoading(true);
        setTrackStatus(!trackStatus);

        try{
            await addCompany(Username , companyName); 
        }catch (error) {
            console.error('Error deleting reply:', error);
        }
        setLoading(false);
    }

    
    return (
        <>
        <div className="inline-block relative bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 leading-tight">
            <h5 className="mb-2 text-lg font-semibold text-gray-900">{companyName}</h5>
            <h5 className="mb-2 text-2xl font-semibold text-gray-900">{jobTitle}</h5>
            
            <p className="mb-3 font-normal text-gray-500">{level}</p>
            { trackStatus? (
                <>
                <button
                    className="text-gray-600 absolute end-3 bottom-12 hover:text-gray-900 font-medium rounded-lg text-sm px-3 py-2"
                    onClick={handleClick}
                    disabled = {loading}
                ><IoBookmarks size={25}/></button>
                </>
            ):(
                <>
                <button
                    className="text-gray-600 absolute end-3 bottom-12 hover:text-gray-900 font-medium rounded-lg text-sm px-3 py-2"
                    onClick={handleClick}
                    disabled = {loading}
                ><IoBookmarksOutline size={25}/></button>
                </>
            )}
        </div>
       
        </>
    );
};
 
export default SearchItem;