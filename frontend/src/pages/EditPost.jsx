import  TitleBar  from '../components/TitleBar';
import * as React from 'react';
import { useState, useRef, useContext, useEffect } from 'react';
import { Link , useNavigate, useParams, useLocation } from "react-router-dom";
import { getpost, editPost, deletePost } from "../utils/client";
import { UsernameContext } from '../context/UsernameContext';



const EditPost = () => {
	const navigate = useNavigate()
    const { post_id } = useParams(); 
    const [categoryId, setCategoryId] = useState(''); 
    const [postTitle, setPostTitle] = useState(''); 
    const [postContent, setPostContent] = useState('');
    const postTitleRef = useRef(null);
    const postContentRef = useRef(null);
    const { Username } = useContext(UsernameContext);

    const location = useLocation();
    const searchParams = new URLSearchParams(location.search);

    useEffect(() => {
        const category_id = searchParams.get('category');
        setCategoryId(parseInt(category_id, 10));
    }, []);


    const getPost = async (post_id) => {
        try {
            const response = await getpost(Username, post_id);
            const content = response['postContent'];
            const title = response['postTitle'];
            setPostTitle(title);
            setPostContent(content);
        } catch (error) {
           /*  if (error.response) {
                console.error('error:', error.response.data);
                console.error('Status code:', error.response.status);
                alert(`${error.response.data}`);
            }  */
        }
        return;
    }
    useEffect(() => {
        getPost(post_id);
    }, [post_id]); 

    const handleEditPost = async() => {
        setPostTitle(postTitleRef.current.value);
        setPostContent(postContentRef.current.value);
        try {
            const response = await editPost(Username, post_id, postTitleRef.current.value, postContentRef.current.value);
            if (response.status === 201) {
                navigate(`/discuss_forum?category=${categoryId}`);
            }
        }catch (error) {
            console.error('Error editing reply:', error);
        }
    };

    const handleDeletePost = async () => {
        try {
            const postIdAsNumber = parseInt(post_id, 10);
            const response = await deletePost(Username, postIdAsNumber);
            if (response.status === 201) {
                navigate(`/discuss_forum?category=${categoryId}`);
            }
        } catch (error) {
            console.error('Error deleting reply:', error);
        }
    };

    return (
        <>
        <TitleBar display={true} currentPage={'discuss forum'}/>
        <div className='items-center mx-24 px-10 mt-6'>
            <div className="grid grid-cols-2 gap-3 md:grid-cols-4 text-lg rounded-lg">
                <button 
                    className={`flex justify-center p-4 ${categoryId === 0 ? 'bg-gray-400 text-white' : 'bg-gray-200'} rounded-lg focus:bg-gray-400 focus:text-white`}
                    disabled={categoryId !== 0} 
                >Interview Experiences</button>
                <button 
                    className={`flex justify-center p-4 ${categoryId === 1 ? 'bg-gray-400 text-white' : 'bg-gray-200'} rounded-lg focus:bg-gray-400 focus:text-white`}
                    disabled={categoryId !== 1} 
                >Interview Questions</button>
                <button 
                    className={`flex justify-center p-4 ${categoryId === 2 ? 'bg-gray-400 text-white' : 'bg-gray-200'} rounded-lg focus:bg-gray-400 focus:text-white`}
                    disabled={categoryId !== 2} 
                >Employee Perks</button>
                <button 
                    className={`flex justify-center p-4 ${categoryId === 3 ? 'bg-gray-400 text-white' : 'bg-gray-200'} rounded-lg focus:bg-gray-400 focus:text-white`}
                    disabled={categoryId !== 3} 
                >Internal Referral</button>
            </div>
            <h2 className='font-bold text-2xl py-4'>Edit Post</h2>
            <input  
				className="bg-gray-200 text-gray-900 lg:text-lg rounded-lg w-full p-2.5"
                ref={postTitleRef}
                placeholder='Write post title...'
				defaultValue={postTitle}
			/>
            <textarea rows="10"
                className="mt-5 bg-gray-100 text-gray-900 lg:text-lg rounded-lg w-full p-2.5"
                ref={postContentRef}
                placeholder='Write post content...'
                defaultValue={postContent}
            />
            <div className='relative'>
            <button
                className="px-9 py-1 bg-green-500 rounded-lg font-sm text-white hover:bg-green-600"
                onClick={handleEditPost}
            >
            Submit
            </button>
            <Link to={{ pathname: "/discuss_forum", search: `?category=${categoryId}` }}>
                <button  
                    className="ml-3 px-9 py-1 bg-gray-600 rounded-lg font-sm text-white hover:bg-gray-700"
                >
                Cancel
                </button>
            </Link>
            <button 
                className="absolute right-0 ml-3 px-9 py-1 bg-red-600 rounded-lg font-sm text-white hover:bg-red-700"
                onClick={handleDeletePost}
            >
            Delete
            </button>
            </div>
        </div>
        </>
    );
};
 
export default EditPost;