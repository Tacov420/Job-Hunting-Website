import  TitleBar  from '../components/TitleBar';
import * as React from 'react';
import { useState } from 'react';


const AddPost = () => {
    const [category, setCategory] = useState('');

    
    const handleAddPost = async() => {

    }

    const handleCancelPost = () =>{

    }

    return (
        <>
        <TitleBar display={true} currentPage={'discuss forum'}/>
        <div className='items-center mx-24 px-10 mt-6'>

            <div className="grid grid-cols-2 gap-3 md:grid-cols-4 text-lg rounded-lg">
                <button 
                    className="flex justify-center p-4 bg-gray-200 rounded-lg focus:bg-gray-400 focus:text-white"
                    onClick={()=>setCategory('experience')}
                >Interview Experiences</button>
                <button 
                    className="flex justify-center p-4 bg-gray-200 rounded-lg focus:bg-gray-400 focus:text-white"
                    onClick={()=>setCategory('questions')}
                >Interview Questions</button>
                <button 
                    className="flex justify-center p-4 bg-gray-200 rounded-lg focus:bg-gray-400 focus:text-white"
                    onClick={()=>setCategory('employee')}
                >Employee Perks</button>
                <button 
                    className="flex justify-center p-4 bg-gray-200 rounded-lg focus:bg-gray-400 focus:text-white"
                    onClick={()=>setCategory('referral')}
                >Internal Referral</button>
            </div>
            <h2 className='font-bold text-2xl py-4'>Add Post</h2>
            <input  
				className="bg-gray-200 text-gray-900 lg:text-lg rounded-lg w-full p-2.5"
                placeholder='Write post title...'
				//ref = {titleRef}
			/>
            <textarea rows="10"
                className="mt-5 bg-gray-100 text-gray-900 lg:text-lg rounded-lg w-full p-2.5"
                placeholder='Write post content...'
                //ref = {titleRef}
            />
            <button 
                className="px-9 py-1 bg-green-500 rounded-lg font-sm text-white hover:bg-green-600"
                onClick={handleAddPost}
            >
            Submit
            </button>
            <button 
                className="ml-3 px-9 py-1 bg-gray-600 rounded-lg font-sm text-white hover:bg-gray-700"
                onClick={handleCancelPost}
            >
            Cancel
            </button>
           
        </div>
        </>
    );
};
 
export default AddPost;