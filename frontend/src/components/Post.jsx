import React from "react";
import { FaPencil } from "react-icons/fa6";
import { Link } from "react-router-dom";

const PostItem = ({id , title , edit, categoryId}) => {
   
    const viewLinkName = 'view/'+ id;
    const editLinkName = 'edit/'+id;
    return (
        <>
        <Link to={{ pathname: viewLinkName, search: `?category=${categoryId}` }}>
        <div className="relative w-full p-4 my-5 bg-gray-100 border border-gray-200 rounded-lg shadow hover:bg-gray-200">
            
            <h5 className="mb-1 text-xl font-bold tracking-tight text-gray-900">{title}</h5>
            <Link to={{ pathname: editLinkName, search: `?category=${categoryId}` }}>
                {edit && 
                (<button className="absolute end-1.5 bottom-3 items-center px-1.5 py-2 text-medium font-medium text-center text-gray-800 bg-blue-200 rounded-lg hover:bg-blue-300 focus:ring-4 focus:outline-none focus:ring-blue-300">
                    <FaPencil size={15} className='inline mr-1.5'/>
                    edit / delete
                </button>)
                }
            </Link>
        </div>
        </Link>
        </>
    );
};
 
export default PostItem;