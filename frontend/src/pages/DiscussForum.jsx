import  TitleBar  from '../components/TitleBar';
import  PostItem  from '../components/Post';
import * as React from 'react';
import { styled } from '@mui/material/styles';
import Paper from '@mui/material/Paper';
import { useState, useEffect, useContext } from 'react';
import { getposts, getUser } from "../utils/client";
import { MdOutlinePostAdd } from "react-icons/md";
import { Routes, Route, Link ,  useNavigate} from "react-router-dom";
import ViewPost from './ViewPost';
import AddPost from './AddPost';
import { UsernameContext } from '../context/UsernameContext';


const Item = styled(Paper)(({ theme }) => ({
  backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
  ...theme.typography.body2,
  padding: theme.spacing(1),
  textAlign: 'center',
  color: theme.palette.text.secondary,
}));


const DiscussForum = () => {
    const [posts, setPosts] = useState([]);
    const [categoryId, setCategoryId] = useState(0);
    const navigate = useNavigate()
    //const { Username } = useContext(UsernameContext);
    const Username = 'test0';
    useEffect(() => {
        const searchParams = new URLSearchParams(window.location.search);
        const initialCategoryId = parseInt(searchParams.get('category'), 10);
        //console.log('category', initialCategoryId);
        chooseCategories(id_to_categories[initialCategoryId]);
        setCategoryId(initialCategoryId);
    }, []);

    const id_to_categories = {0: 'experience', 1: 'questions', 2: 'employee', 3: 'referral'}

    const category_ids = {'experience': 0, 'questions':1 , 'employee':2 , 'referral':3 }
    const chooseCategories = async (category) => {
        const category_id2 = category_ids[category];
        setCategoryId(category_id2);
        try {
            const response = await getposts(encodeURIComponent(Username), category_id2);
            const keys = Object.keys(response); //postIds
            const posts = keys.map(key => ({
                id: key,
                title: response[key][0],
                edit: response[key][2]
            }));
            setPosts(posts);
            //console.log('posts', posts);
        } catch (error) {
/*             if (error.response) {
                console.error('error:', error.response.data);
                console.error('Status code:', error.response.status);
                alert(`${error.response.data}`);
            }  */
        }
        return;
    }

    const handleAddPost = async() => {
        navigate('/discuss_forum/add')
    }

    return (
        <>
        <TitleBar display={true} currentPage={'discuss forum'}/>
        <div className='items-center mx-24 px-10 mt-6'>
            <div className="grid grid-cols-2 gap-3 md:grid-cols-4 text-lg rounded-lg">
                <button 
                    className={`flex justify-center p-4 ${categoryId === 0 ? 'bg-gray-400 text-white' : 'bg-gray-200'} rounded-lg focus:bg-gray-400 focus:text-white`}
                    onClick={()=>chooseCategories('experience')}

                >Interview Experiences</button>
                <button 
                    className={`flex justify-center p-4 ${categoryId === 1 ? 'bg-gray-400 text-white' : 'bg-gray-200'} rounded-lg focus:bg-gray-400 focus:text-white`}
                    onClick={()=>chooseCategories('questions')}
                >Interview Questions</button>
                <button 
                    className={`flex justify-center p-4 ${categoryId === 2 ? 'bg-gray-400 text-white' : 'bg-gray-200'} rounded-lg focus:bg-gray-400 focus:text-white`}
                    onClick={()=>chooseCategories('employee')}
                >Employee Perks</button>
                <button 
                    className={`flex justify-center p-4 ${categoryId === 3 ? 'bg-gray-400 text-white' : 'bg-gray-200'} rounded-lg focus:bg-gray-400 focus:text-white`}
                    onClick={()=>chooseCategories('referral')}
                >Internal Referral</button>
            </div>
            <Link to={{ pathname: "/discuss_forum/add", search: `?category=${categoryId}` }}>
                <button 
                    className="px-3 py-2 bg-gray-800 rounded-lg font-semibold text-white mt-5 hover:bg-gray-600"
                    onClick={handleAddPost}
                >
                    <MdOutlinePostAdd className='inline mr-3' size={21}/>
                    Add Post
                </button>
            </Link>
            <div>
                {posts.map(post => (
                    <Link key={post.id} to={`/discuss_forum/view/${post.id}`}>
                        <PostItem id={post.id} title={post.title} edit = {post.edit} categoryId={categoryId}/>
                    </Link>
                ))}
            </div>
        </div>

            <Routes>
                <Route path="add" element={<AddPost/>} />
                <Route path="view/:post_id" element={<ViewPost />} />
            </Routes>
        </>
    );
};
 
export default DiscussForum;
