import React from "react";
import { FaPencil } from "react-icons/fa6";
import { Routes, Route, Link , useParams} from "react-router-dom";

const ReplyItem = ({id , content , edit}) => {
    
    //const linkName = 'discuss_forum/'+ id;
    return (
        <>
        <Link to={''}> {/* 要改 */}
        <div class="relative w-full p-3 my-5 bg-gray-50 border border-gray-200 rounded-lg hover:bg-gray-100">
            
            <h5 class="mb-1 text-medium font-medium tracking-tight text-gray-900">{content}</h5>
            {edit && 
            (<button class="absolute end-1.5 bottom-3 items-center px-1.5 py-1 text-medium font-medium text-center text-gray-800 bg-blue-200 rounded-lg hover:bg-blue-300 focus:ring-4 focus:outline-none focus:ring-blue-300">
                <FaPencil size={15} className='inline mr-1.5'/>
                edit / delete
            </button>)
            }
        </div>
        </Link>
        </>
    );
};
 
export default ReplyItem;