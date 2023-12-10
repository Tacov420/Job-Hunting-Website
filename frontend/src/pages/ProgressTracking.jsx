import React, {useState , useRef , useContext , useEffect} from "react";
import { UsernameContext } from '../context/UsernameContext';
import  TitleBar  from '../components/TitleBar';
import NewJobDialog from '../components/NewJobDialog';
import { RiDeleteBin5Fill } from "react-icons/ri";
import { MdOutlinePostAdd } from "react-icons/md";
import { Routes, Route, Link , useParams} from "react-router-dom";
import {getColor , getProgresses ,  deleteProgress} from '../utils/client';


const ProgressTracking = () => {
    const [newDialogOpen, setNewDialogOpen] = useState(false);
    const [progresses, setProgresses] = useState([]);
    const { Username } = useContext(UsernameContext);
    const statusTable = ['Unknown', 'Accepted' , 'Rejected' , 'Quit'];

    useEffect(() => {    
        getProgressList();
    }, []); 

    const getProgressList = async () => {
        try{
            const response = await getProgresses(Username);
            const keys = Object.keys(response);
            const ProgressList = keys.map(key => ({
                id: key,
                companyName: response[key][0],
                jobTitle: response[key][1],
                latestStage: response[key][2][response[key][2].length - 1],
                latestDate: response[key][3][response[key][3].length - 1],  
                latestStatus: statusTable[response[key][4][response[key][4].length - 1]],
                color: getColor(response[key][4][response[key][4].length - 1]),
            }));
            setProgresses(ProgressList);
            
        }catch (error) {
            if (error.response) {
                alert(`${error.response.data}`);
            } 
        }
        return;
    }

    const handleDeleteProgress = async (progressId) => {
        try {
            const response = await deleteProgress(Username, progressId);
            if (response.status === 201) {
                setProgresses((preProgresses) => preProgresses.filter((progress) => progress.id !== progressId));
            }
        } catch (error) {
            console.error('Error deleting progress:', error);
        }
        return;
    }

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
                        {progresses.map(progress => (
                            <tr className="bg-white border-b hover:bg-gray-50">
                                <th scope="row" className="px-6 py-4 font-medium text-gray-900 whitespace-nowrap">
                                    <Link to={`/progress_tracking/${progress.id}`}> 
                                    {progress.companyName}
                                    </Link>
                                </th>
                                <td className="px-6 py-3">
                                    {progress.jobTitle}
                                </td>

                                <td className={`px-6 py-4 ${progress.color} font-semibold`}>
                                    {progress.latestDate} {progress.latestStage}
                                    <span className="text-xs"> ({progress.latestStatus})</span>
                                </td>
                                
                                <td className="px-6 py-3">
                                    <button type="button"
                                        onClick={()=>handleDeleteProgress(progress.id)}>
                                    <RiDeleteBin5Fill size={25} className="text-red-600 hover:text-red-700"/>
                                    </button>
                                </td>
                            </tr>
                        ))}
 
                    </tbody>
                </table>
            </div>
        </div>
        <NewJobDialog open={newDialogOpen} onClose={() => setNewDialogOpen(false)} progresses = {progresses} setNewProgress={setProgresses}/>
        </>
    );
};
 
export default ProgressTracking;