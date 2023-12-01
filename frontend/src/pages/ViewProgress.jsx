import React, {useState} from "react";
import  TitleBar  from '../components/TitleBar';
import NewStatusDialog from '../components/NewStatusDialog';
import EditStatusDialog from '../components/EditStatusDialog';

import { MdAddCircle } from "react-icons/md";
import { Routes, Route, Link , useParams} from "react-router-dom";


const ProgressTracking = () => {
    const [newStatusDialogOpen, setNewStatusDialogOpen] = useState(false);
    const [editStatusDialogOpen, setEditStatusDialogOpen] = useState(false);
    const handleClick = () =>{
        setEditStatusDialogOpen(true);
    }

    return (
        <>
        <TitleBar display={true} currentPage={'progress tracking'}/>
        <div className='items-center mx-24 px-10 mt-6'>
            <button 
                className="px-3 py-1.5 bg-gray-800 rounded-lg font-semibold text-white mt-3 mb-4 hover:bg-gray-600"
                onClick={()=>setNewStatusDialogOpen(true)}
            >
                <MdAddCircle className='inline mr-3' size={21}/>
                Add Status
            </button>
            <div>
                <table className="w-full text-lg text-base/loose text-left text-gray-500">
                    <thead className="text-lg font-bold text-gray-700">
                        <tr>
                            <th scope="col" className="px-6 py-3">
                                Company
                            </th>
                            <th scope="col" className="px-6 py-3">
                                Job Title
                            </th>
                            <th scope="col" className="px-6 py-3">
                                Stage (click and edit)
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr className="bg-white">
                            <th scope="row" className="px-6 py-4 font-medium text-gray-900 whitespace-nowrap">
                                McDonald's
                            </th>
                            <td className="px-6 py-3">
                                Fries Quality Control Engineer
                            </td>
                            <td className="flex">
                                <div className="inline px-6 py-3 bg-green-300 text-green-800 hover:bg-green-400"
                                    onClick={handleClick}>
                                    7/29 Send Resume
                                </div>
                                <div className="inline px-6 py-3 bg-green-300 text-green-800 hover:bg-green-400"
                                    onClick={handleClick}>
                                    8/3 First Interview
                                </div>
                                <div className="inline px-6 py-3 bg-red-300 text-red-800 hover:bg-red-400"
                                    onClick={handleClick}>
                                    8/7 Rejected
                                </div>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <NewStatusDialog open={newStatusDialogOpen} onClose={() => setNewStatusDialogOpen(false)}/>
        <EditStatusDialog open={editStatusDialogOpen} onClose={() => setEditStatusDialogOpen(false)}/>
        </>
    );
};
 
export default ProgressTracking;