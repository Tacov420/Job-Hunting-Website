import React, { useContext, useState, useRef, useEffect } from 'react';
import { UsernameContext } from '../context/UsernameContext';
import { Link , useNavigate } from "react-router-dom";
import  TitleBar  from '../components/TitleBar';
import SearchItem from '../components/SearchItem';
import {getSearchResult} from '../utils/client';


const HomePage = () => {
    const { Username, updateUsername } = useContext(UsernameContext);
	const navigate = useNavigate();
    const [level, setLevel] = useState('');
    const jobTitleRef = useRef(null);
    const CompanyRef = useRef(null);
    const [SearchResult , setSearchResult] = useState([]);

	useEffect(() => {
        //getRecommendation();
	}, []);

    const handleChange = (e) => {
        setLevel(e.target.value);
    };

    const getRecommendation = async() => {
        
    }

    const handleSubmit = async() =>{
        const jobTitle = jobTitleRef.current?.value ?? "";
		const company = CompanyRef.current?.value ?? "";
        
        try{
            console.log(level);
            //const response = await getSearchResult(Username , jobTitle , company , level)
            //const keys = Object.keys(response);
            //const resultList = keys.map(key => ({
            //    id: key,
            //    companyName: response[key][0]['companyName'],
            //    jobTitle: response[key][0]['jobTitle'],
            //   isTrack: response[key][0]['isTrack']
            //}));
            //setSearchResult(resultList);
        }catch(error){
            alert(error);
        }
        return;
    }

    
    return (
        <>
        <TitleBar display={true} currentPage={'home'}/>
        <div className="max-w-screen-lg mx-auto">
            <div className="md:col-span-8 pt-5">
                <div className="flex flex-wrap -mx-3 mb-5">
                    <div className="w-full md:w-3/5 px-3 mb-6 md:mb-0">
                        <div className="relative">
                            <div className="absolute inset-y-0 start-0 flex items-center ps-3 pointer-events-none">
                                <svg className="w-4 h-4 text-gray-500" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 20">
                                    <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m19 19-4-4m0-7A7 7 0 1 1 1 8a7 7 0 0 1 14 0Z"/>
                                </svg>
                            </div>
                            <input type="search" id="default-search" 
                                className="block w-full p-2 ps-10 text-lg text-gray-900 rounded-lg bg-gray-200" 
                                placeholder="Search Job's Title..."
                                ref = {jobTitleRef}/>
                        </div>
                    </div>

                    <div className="w-full md:w-2/5 px-3 mb-6 md:mb-0">
                        <div className="relative">
                        <div className="absolute inset-y-0 start-0 flex items-center ps-3 pointer-events-none">
                            <svg className="w-4 h-4 text-gray-500" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 20">
                                <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m19 19-4-4m0-7A7 7 0 1 1 1 8a7 7 0 0 1 14 0Z"/>
                            </svg>
                        </div>
                        <select id="small" 
                            className="block w-full p-3 ps-10 text-lg text-gray-500 rounded-lg bg-gray-200"
                            defaultValue = {'DEFAULT'} onChange={handleChange}
                        >
                            <option value="DEFAULT" selected disabled>Level...</option>
                            <option 
                                value="0"
                                key='level0'
                                className="py-4 text-lg bg-white text-gray-500 hover:bg-gray-600 hover:text-white"
                                >level 0 : Entry-level
                            </option>
                            <option 
                                value="1"
                                key='level1'
                                className="py-4 text-lg bg-white text-gray-500 hover:bg-gray-600 hover:text-white"
                                >level 1 : Mid-senior level
                            </option>       
                        </select>
                        </div>
                    </div>
                </div>
                <div className="flex flex-wrap -mx-3 mb-2">
                    <div className="w-full px-3 mb-6 md:mb-0">
                        <div className="relative">
                            <div className="absolute inset-y-0 start-0 flex items-center ps-3 pointer-events-none">
                                <svg className="w-4 h-4 text-gray-500" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 20">
                                    <path stroke="currentColor" strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="m19 19-4-4m0-7A7 7 0 1 1 1 8a7 7 0 0 1 14 0Z"/>
                                </svg>
                            </div>
                            <input type="search" id="default-search" 
                                className="block w-full p-2 ps-10 text-lg text-gray-900 rounded-lg bg-gray-200" 
                                placeholder="Company..."
                                ref = {CompanyRef}/>
        
                        </div>
                    </div>
                </div>
                <button type="submit" 
                    className="text-white bg-gray-500 hover:bg-gray-700 font-medium rounded-lg text-medium px-5 py-1 mb-2 mt-1"
                    onClick={handleSubmit}
                >Search</button>

                <div className="w-full">
                    <div className="relative flex py-4 items-center text-lg">
                        <div className="flex-grow border-t-2 border-gray-300"></div>
                        <span className="flex-shrink mx-4 text-gray-400">Search Result</span>
                        <div className="flex-grow border-t-2 border-gray-300"></div>
                    </div>
                    <div className="grid grid-cols-2 gap-3">
                    {SearchResult.map(result => (
                        <SearchItem 
                            id={result.id} 
                            companyName={result.companyName}
                            jobTitle = {result.jobTitle}
                            isTrack={result.isTrack}
                        />
                    ))} 
                    </div>
                </div>
            </div>  
        </div>


        </>
    );
};

export default HomePage;
