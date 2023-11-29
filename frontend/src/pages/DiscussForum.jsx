import  TitleBar  from '../components/TitleBar';
import  PostItem  from '../components/Post';
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
import ViewPost from './ViewPost';
import AddPost from './AddPost';



const Item = styled(Paper)(({ theme }) => ({
  backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
  ...theme.typography.body2,
  padding: theme.spacing(1),
  textAlign: 'center',
  color: theme.palette.text.secondary,
}));


const DiscussForum = () => {
    //const [posts, setPosts] = useState([]);

    //const defaultPosts = await getposts('experience');
    
    const chooseCatagories = (category) => {
        console.log(category);
        
    }

    const handleAddPost = async() => {

    }
    return (
        <>
        <TitleBar display={true} currentPage={'discuss forum'}/>
        <div className='items-center mx-24 px-10 mt-6'>
            <div className="grid grid-cols-2 gap-3 md:grid-cols-4 text-lg rounded-lg">
                <button 
                    className="flex justify-center p-4 bg-gray-200 rounded-lg focus:bg-gray-400 focus:text-white"
                    onClick={()=>chooseCatagories('experience')}
                >Interview Experiences</button>
                <button 
                    className="flex justify-center p-4 bg-gray-200 rounded-lg focus:bg-gray-400 focus:text-white"
                    onClick={()=>chooseCatagories('questions')}
                >Interview Questions</button>
                <button 
                    className="flex justify-center p-4 bg-gray-200 rounded-lg focus:bg-gray-400 focus:text-white"
                    onClick={()=>chooseCatagories('employee')}
                >Employee Perks</button>
                <button 
                    className="flex justify-center p-4 bg-gray-200 rounded-lg focus:bg-gray-400 focus:text-white"
                    onClick={()=>chooseCatagories('referral')}
                >Internal Referral</button>
            </div>
            <button 
                className="px-3 py-2 bg-gray-800 rounded-lg font-semibold text-white mt-5 hover:bg-gray-600"
                onClick={handleAddPost}
            >
                <MdOutlinePostAdd className='inline mr-3' size={21}/>
                Add Post
            </button>
            <div>
                <PostItem id={'123456789'} title={"post title1"} edit = {true} />
                <PostItem id={'123'} title={"post title2"} edit = {false} />
            </div>
        </div>
        </>
    );
};
 
export default DiscussForum;