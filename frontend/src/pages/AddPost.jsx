import  TitleBar  from '../components/TitleBar';
import * as React from 'react';
import { useState, useEffect, useRef, useContext } from 'react';
import { Link , useNavigate, useLocation } from "react-router-dom";
import { addPost } from "../utils/client";
import { UsernameContext } from '../context/UsernameContext';

const AddPost = () => {
    const [categoryId, setCategoryId] = useState('');
    const location = useLocation();
    const searchParams = new URLSearchParams(location.search);
    //const { Username } = useContext(UsernameContext);
    const Username = 'test0'
    const navigate = useNavigate();
    const titleRef = useRef(null);
    const contentRef = useRef(null);
    useEffect(() => {
        const category_id = searchParams.get('category');
        setCategoryId(parseInt(category_id, 10));
    }, []);

    const handleAddPost = async() => {
        const title = titleRef.current.value;
        const content = contentRef.current.value;
        try {
            const response = await addPost(Username, categoryId, title, content);
            if (response.status === 201){
                navigate(`/discuss_forum?category=${categoryId}`);
            }
        } catch (error) {
            if (error.response) {
                console.error('error:', error.response.data);
                console.error('Status code:', error.response.status);
                alert(`${error.response.data}`);
            } 
        }
    }

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
            <h2 className='font-bold text-2xl py-4'>Add Post</h2>
            <label htmlFor="title" >Title</label>
            <input  
                id="title"
                className="bg-gray-200 text-gray-900 lg:text-lg rounded-lg w-full p-2.5"
                placeholder='Write post title...'
                ref = {titleRef}
            />
            <label htmlFor="content" >Content</label>
            <textarea 
                id="content"
                rows="10"
                className="mt-5 bg-gray-100 text-gray-900 lg:text-lg rounded-lg w-full p-2.5"
                placeholder='Write post content...'
                ref = {contentRef}
            />
            <button 
                className="px-9 py-1 bg-green-500 rounded-lg font-sm text-white hover:bg-green-600"
                onClick={handleAddPost}
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
           
        </div>
        </>
    );
};
 
export default AddPost;
