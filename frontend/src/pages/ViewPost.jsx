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
import { Routes, Route, Link , useParams} from "react-router-dom";




const ViewPost = () => {
    //const [posts, setPosts] = useState([]);

    //const defaultPosts = await getposts('experience');
    
    const chooseCatagories = (catagory) => {
        console.log(catagory);
        
    }

    const handleAddReply = async() => {

    }
    return (
        <>
        <TitleBar display={true} currentPage={'discuss forum'}/>
        <div className='items-center mx-24 px-10 mt-6'>
            <input  
				className="bg-gray-200 text-gray-900 lg:text-lg rounded-lg w-full p-2.5"
				defaultValue='Post title' readOnly
			/>
            <textarea rows="10"
                className="mt-5 bg-gray-100 text-gray-900 lg:text-lg rounded-lg w-full p-2.5"
                defaultValue='Post content' readOnly
            />
            <button 
                className="px-3 py-2 bg-gray-800 rounded-lg font-semibold text-white mt-5 hover:bg-gray-600"
                onClick={handleAddReply}
            >
                <MdOutlinePostAdd className='inline mr-3' size={21}/>
                Add Reply
            </button>
            <div>
                <ReplyItem id={'123456789'} content={"reply1"} edit = {true} />
                <ReplyItem id={'123'} content={"reply2"} edit = {false} />
            </div>
        </div>
        </>
    );
};
 
export default ViewPost;