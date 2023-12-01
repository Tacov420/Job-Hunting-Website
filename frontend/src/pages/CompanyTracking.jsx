import React, {useState , useRef} from "react";
import  TitleBar  from '../components/TitleBar';
import  CompanyItem  from '../components/Company';
import { Routes, Route, Link , useParams} from "react-router-dom";

const CompanyTracking = () => {
    const [companies, setCompanies] = useState([]);

    //const companyList = await getCompanies();
    //setCompanies(companyList);


    const deleteCompany = (id) => {
        //api
        //setCompanies(companies.filter((company) => company.id !== id));
    };


    return (
        <>
        <TitleBar display={true} currentPage={'company tracking'}/>
        <div className='items-center mx-24 px-10 mt-6'>
            <div className="flex flex-wrap -mx-3 mb-5">
                <div className="w-full md:w-full px-3 mb-6 md:mb-0">
                    <div className="relative">
                        <div className="absolute inset-y-0 start-0 flex items-center ps-3 pointer-events-none">
                            <svg className="w-4 h-4 text-gray-500" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 20">
                                <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m19 19-4-4m0-7A7 7 0 1 1 1 8a7 7 0 0 1 14 0Z"/>
                            </svg>
                        </div>
                        <input type="search" id="default-search" 
                            className="!outline-none block w-full p-2 ps-10 text-lg text-gray-900 rounded-lg bg-gray-200" 
                            placeholder="Search Company..."/>
                        <button className="absolute end-1.5 bottom-1.5 items-center px-1.5 py-1 text-medium font-medium text-center text-white bg-blue-500 rounded-lg hover:bg-blue-400">
                            search
                        </button>
                    </div>
                </div>
            </div>
            
            <CompanyItem 
                id={'123'} 
                CompanyName={'company 1'}
                email={true} 
                onDelete={()=>deleteCompany('123')} 
            />
            <CompanyItem 
                id={'456'} 
                CompanyName={'company 2'} 
                email={false} 
                onDelete={()=>deleteCompany('456')} 
            />     
        </div>
        </>
    );
};
 
export default CompanyTracking;