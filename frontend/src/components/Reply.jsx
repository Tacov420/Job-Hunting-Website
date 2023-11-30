import React, {useState , useRef} from "react";
import { FaPencil } from "react-icons/fa6";
import { Routes, Route, Link , useParams} from "react-router-dom";

const ReplyItem = ({id , content , edit}) => {
    const [editMode , setEditMode] = useState(false);
    const contentRef = useRef(null);

    const handleCancel = () =>{
        setEditMode(false);
    }
    
    const handleSubmit = async() =>{
        //api part
        const content = contentRef.current.value;
        console.log(content);
        if (content == ''){
            setEditMode(false);
        }
        navigate('/discuss_forum/view');
    }
    const handleDelete = () =>{
        setEditMode(false);
    }

    return (
        <> 
        {editMode? (
            <>
            <form id="inputArea">
            <div className="w-full mb-4 mt-2 border-2 border-blue-300 rounded-lg bg-gray-50">
                
                <textarea rows="3" 
                    className="!outline-none w-full p-2 text-medium text-gray-900 bg-white rounded-lg" 
                    ref={contentRef}
                    defaultValue = {content}
                   >
                </textarea>
                
                <div className="relative items-right px-3 py-1">
                    <button 
                        className="px-9 py-1 bg-green-400 rounded-lg font-sm text-white hover:bg-green-600"
                        onClick={handleSubmit}
                    >
                    Submit
                    </button>
                    <button 
                        className="ml-3 px-9 py-1 bg-gray-400 rounded-lg font-sm text-white hover:bg-gray-700"
                        onClick={handleCancel}
                    >
                    Cancel
                    </button>
                    <button 
                        className="absolute right-2 ml-3 px-9 py-1 bg-red-400 rounded-lg font-sm text-white hover:bg-red-700"
                        onClick={handleDelete}
                    >
                    Delete
                    </button>
                </div>
            </div>
            </form>
            </>
        ):
        (
            <div className="relative w-full p-3 my-5 bg-gray-50 border border-gray-200 rounded-lg hover:bg-gray-100">
            
            <h5 className="mb-1 text-medium font-medium tracking-tight text-gray-900">{content}</h5>
            {edit && 
            (<button 
                className="absolute end-1.5 bottom-3 items-center px-1.5 py-1 text-medium font-medium text-center text-gray-800 bg-blue-200 rounded-lg hover:bg-blue-300 focus:ring-4 focus:outline-none focus:ring-blue-300"
                onClick={()=>setEditMode(true)}
            >
                <FaPencil size={15} className='inline mr-1.5'/>
                edit / delete
            </button>)
            }
            </div>
        )}
        
        </>
    );
};
 
export default ReplyItem;