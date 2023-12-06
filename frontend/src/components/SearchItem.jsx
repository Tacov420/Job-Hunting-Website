import React, { useState } from "react";
import { IoBookmarks } from "react-icons/io5";
import { IoBookmarksOutline } from "react-icons/io5";

const SearchItem = ({id , companyName , jobTitle, location , isTrack}) => {
    const [track, setTrack] = useState(isTrack);

    const handleClick = ()=>{
        //api
        setTrack(!track);
        return;
    }

  
    return (
        <>
        <div className="inline-block relative bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 leading-tight">
            <h5 className="mb-2 text-lg font-semibold text-gray-900">{companyName}</h5>
            <h5 className="mb-2 text-2xl font-semibold text-gray-900">{jobTitle}</h5>
            
            <p className="mb-3 font-normal text-gray-500">{location}</p>
            { track? (
                <>
                <button
                    className="text-gray-600 absolute end-3 bottom-12 hover:text-gray-900 font-medium rounded-lg text-sm px-3 py-2"
                    onClick={handleClick}
                ><IoBookmarks size={25}/></button>
                </>
            ):(
                <>
                <button
                    className="text-gray-600 absolute end-3 bottom-12 hover:text-gray-900 font-medium rounded-lg text-sm px-3 py-2"
                    onClick={handleClick}
                ><IoBookmarksOutline size={25}/></button>
                </>
            )}
        </div>
       
        </>
    );
};
 
export default SearchItem;