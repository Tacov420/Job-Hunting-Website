import  TitleBar  from '../components/TitleBar';
import  ReplyItem  from '../components/Reply';
import * as React from 'react';
import { styled } from '@mui/material/styles';
import Box from '@mui/material/Box';
import Paper from '@mui/material/Paper';
import Grid from '@mui/material/Grid';
import { useState, useEffect, useRef, useContext } from 'react';
import { addReply, getpost, deleteReply } from "../utils/client";
import { MdOutlinePostAdd } from "react-icons/md";
import { FaPencil } from "react-icons/fa6";
import { useParams } from "react-router-dom";
import { UsernameContext } from '../context/UsernameContext';



const ViewPost = () => {
    const [displayInput , setDisplayInput] = useState(false);
    const { post_id } = useParams(); 
    const [postTitle, setPostTitle] = useState(''); 
    const [postContent, setPostContent] = useState('');
    const [replies, setReplies] = useState([]);
    const contentRef = useRef(null);
    const { Username } = useContext(UsernameContext);

    const getPost = async (post_id) => {
        try {
            const response = await getpost(Username, post_id);
            const { replies: [reply], postContent: content, postTitle: title } = response;  //reply = response['replies'][0];
            setPostTitle(title);
            setPostContent(content);
            const keys = Object.keys(reply); //replyIds
            const replies = keys.map(key => ({
                id: parseInt(key, 10),
                content: reply[key][0],
                edit: reply[key][2]
            }));
            setReplies(replies);
        } catch (error) {
            if (error.response) {
                console.error('error:', error.response.data);
                console.error('Status code:', error.response.status);
                alert(`${error.response.data}`);
            } 
        }
        return;
    }
    useEffect(() => {
        console.log(post_id)
        getPost(post_id);
    }, [post_id]); 

    /*useEffect(() => {
        if (Username) {
            getPost(post_id, Username);
        }
    }, [post_id, Username]);*/

    const handleAddReply = () => {
        setDisplayInput(true);
    }
    const handleCancel = () =>{
        setDisplayInput(false);
        getPost(post_id);
    }
    
    const handleSubmit = async() =>{
        const replyContent = contentRef.current.value;
        try{
            const response = await addReply(Username, post_id, replyContent);
        } catch (error) {
            if (error.response) {
                console.error('error:', error.response.data);
                console.error('Status code:', error.response.status);
                alert(`${error.response.data}`);
            } 
        }
        await getPost(post_id);
        setDisplayInput(false);
        
    }

    const handleDeleteReply = async (replyId) => {
        try {
            const response = await deleteReply(Username, replyId);
            if (response.status === 201) {
                setReplies((prevReplies) => prevReplies.filter((reply) => reply.id !== replyId));
            }
            
        } catch (error) {
            console.error('Error deleting reply:', error);
        }
    };

    return (
        <>
        <TitleBar display={true} currentPage={'discuss forum'}/>
        <div className='items-center mx-24 px-10 mt-6 overflow-y-auto'>
            <input  
				className="bg-gray-200 text-gray-900 lg:text-lg rounded-lg w-full p-2.5"
				defaultValue={postTitle} readOnly
			/>
            <textarea rows="10"
                className="mt-5 bg-gray-100 text-gray-900 lg:text-lg rounded-lg w-full p-2.5"
                defaultValue={postContent} readOnly
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
            {replies.map(reply => (
                    <ReplyItem key={reply.id} replyId={reply.id} postId={post_id} content={reply.content} edit = {reply.edit} onDelete={handleDeleteReply} setReplies={setReplies}  />
                ))}

            </div>
            {displayInput && (
                <>
                
                <form id="inputArea" >
                <div className="w-full mb-4 border-2 border-blue-300 rounded-lg bg-gray-50">
                    
                    <textarea rows="3" 
                        ref={contentRef}
                        className="!outline-none w-full p-2 text-medium text-gray-900 bg-white rounded-lg" 
                        placeholder="Write a comment...">
                    </textarea>
                    
                    <div className="flex items-right px-3 py-1">
                        <button 
                            type="button"
                            className="px-9 py-1 bg-green-400 rounded-lg font-sm text-white hover:bg-green-600"
                            onClick={handleSubmit}
                        >
                        Submit
                        </button>
                        <button 
                            type="button"
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
