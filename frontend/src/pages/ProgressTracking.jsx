import React, {useState} from "react";
import  TitleBar  from '../components/TitleBar';
import NewJobDialog from '../components/NewJobDialog';
import { RiDeleteBin5Fill } from "react-icons/ri";
import { MdOutlinePostAdd } from "react-icons/md";
import { Routes, Route, Link , useParams} from "react-router-dom";


const ProgressTracking = () => {
    const [newDialogOpen, setNewDialogOpen] = useState(false);

    return (
        <>
        <TitleBar display={true} currentPage={'progress tracking'}/>
        <div className='items-center mx-24 px-10 mt-6'>
            <button 
                className="px-3 py-1.5 bg-gray-800 rounded-lg font-semibold text-white mt-3 mb-4 hover:bg-gray-600"
                onClick={()=>setNewDialogOpen(true)}
            >
                <MdOutlinePostAdd className='inline mr-3' size={21}/>
                Add Job
            </button>
            <div className="relative overflow-x-auto shadow-md sm:rounded-lg">
                <table className="w-full text-lg text-base/loose text-left text-gray-500 ">
                    <thead className="text-lg font-bold text-gray-700 bg-gray-100 ">
                        <tr>
                            <th scope="col" className="px-6 py-3">
                                Company
                            </th>
                            <th scope="col" className="px-6 py-3">
                                Job Title
                            </th>
                            <th scope="col" className="px-6 py-3">
                                Latest Status
                            </th>
                            <th scope="col" className="px-6 py-3">
                                Delete
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr className="bg-white border-b hover:bg-gray-50">
                            <th scope="row" className="px-6 py-4 font-medium text-gray-900 whitespace-nowrap">
                                <Link to={'/progress_tracking/view'}>  {/*值要改! (如果Link整列的話格式會跑掉QQ)*/}
                                McDonald's
                                </Link>
                            </th>
                            <td className="px-6 py-3">
                                Fries Quality Control Engineer
                            </td>
                            <td className="px-6 py-4 bg-red-300 text-red-800 font-semibold">
                                8/7 Rejected
                            </td>
                            
                            <td className="px-6 py-3">
                                <button type="button">
                                <RiDeleteBin5Fill size={25} className="text-red-600 hover:text-red-700"/>
                                </button>
                            </td>
                        </tr>
                        <tr className="bg-white border-b hover:bg-gray-50">
                            <th scope="row" className="px-6 py-4 font-medium text-gray-900 whitespace-nowrap">
                                <Link to={"/progress_tracking/view"}>
                                Burger King
                                </Link>
                            </th>
                            <td className="px-6 py-3">
                                Burger Queen
                            </td>
                            <td className="px-6 py-4 bg-green-400 text-green-800 font-semibold">
                                8/9 Accepted
                            </td>
                            <td className="px-6 py-4">
                                <button type="button">
                                <RiDeleteBin5Fill size={25} className="text-red-600 hover:text-red-700"/>
                                </button>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <NewJobDialog open={newDialogOpen} onClose={() => setNewDialogOpen(false)}/>
        </>
    );
};
 
export default ProgressTracking;