import  TitleBar  from '../components/TitleBar';
import  ReplyItem  from '../components/Reply';
import * as React from 'react';
import { styled } from '@mui/material/styles';
import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import Grid from '@mui/material/Grid';
import { useState } from 'react';
import { getposts } from "../utils/client";
import { MdOutlinePostAdd } from "react-icons/md";
import { FaPencil } from "react-icons/fa6";
import { Routes, Route, Link , useParams, useNavigate} from "react-router-dom";




const ViewPost = () => {
    const [replies, setReplies] = useState([]);
    const [displayInput , setDisplayInput] = useState(false);
	const navigate = useNavigate()

    //const defaultPosts = await getposts('experience');
    
    const chooseCatagories = (catagory) => {
        console.log(catagory);
        
    }

    const handleAddReply = () => {
        setDisplayInput(true);
    }
    const handleCancel = () =>{
        setDisplayInput(false);
        navigate('/discuss_forum/view');
    }
    
    const handleSubmit = async() =>{

    }
    return (
        <>
        <TitleBar display={true} currentPage={'discuss forum'}/>
        <div className='items-center mx-24 px-10 mt-6 overflow-y-auto'>
            <input  
				className="bg-gray-200 text-gray-900 lg:text-lg rounded-lg w-full p-2.5"
				defaultValue='Post title' readOnly
			/>
            <textarea rows="10"
                className="mt-5 bg-gray-100 text-gray-900 lg:text-lg rounded-lg w-full p-2.5"
                defaultValue='Post content' readOnly
            />
            <a href='#inputArea'>
            <button 
                className="px-3 py-2 bg-gray-800 rounded-lg font-semibold text-white mt-5 hover:bg-gray-600"
                onClick={handleAddReply}
            >
                <MdOutlinePostAdd className='inline mr-3' size={21}/>
                Add Reply
            </button>
            </a>
            <div>
                <ReplyItem id={'123456789'} content={"reply1"} edit = {true} />
                <ReplyItem id={'123'} content={"reply2"} edit = {false} />
            </div>
            {displayInput && (
                <>
                <form id="inputArea">
                <div className="w-full mb-4 border-2 border-blue-300 rounded-lg bg-gray-50">
                    
                    <textarea rows="3" 
                        className="!outline-none w-full p-2 text-medium text-gray-900 bg-white rounded-lg" 
                        placeholder="Write a comment...">
                    </textarea>
                    
                    <div className="flex items-right px-3 py-1">
                        <button 
                            className="px-9 py-1 bg-green-400 rounded-lg font-sm text-white hover:bg-green-600"
                            onClick={handleSubmit}
                        >
                        Submit
                        </button>
                        <button 
                            className="ml-3 px-9 py-1 bg-gray-600 rounded-lg font-sm text-white hover:bg-gray-700"
                            onClick={handleCancel}
                        >
                        Cancel
                        </button>
                    </div>
                </div>
                </form>
                </>
            )}
        </div>
        </>
    );
};
 
export default ViewPost;