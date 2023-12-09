import React, {useState , useEffect , useContext , useRef} from "react";
import { UsernameContext } from '../context/UsernameContext';

import  TitleBar  from '../components/TitleBar';
import NewStatusDialog from '../components/NewStatusDialog';
import EditStatusDialog from '../components/EditStatusDialog';
import { getProgress, addStage, updateStage, getColorHover  } from "../utils/client";
import { MdAddCircle } from "react-icons/md";
import { Routes, Route, Link , useParams} from "react-router-dom";


const ProgressTracking = () => {
    const [newStatusDialogOpen, setNewStatusDialogOpen] = useState(false);
    const [editStatusDialogOpen, setEditStatusDialogOpen] = useState(false);
    const { progress_id } = useParams(); 
    const [companyName , setCompanyName] = useState('');
    const [jobTitle , setJobTitle] = useState('');
    const [stages , setStages] = useState([]);
    const [EditIndex , setEditIndex] = useState(null);
    const [EditStatus , setEditStatus] = useState(null);

    const statusTable = ['Unknown', 'Accepted' , 'Rejected' , 'Quit'];
    const { Username } = useContext(UsernameContext);


    useEffect(() => {    
        getProgressData();
    }, []); 


    const handleClick = (index ,  status) =>{
        setEditIndex(index);
        setEditStatus(status);
        setEditStatusDialogOpen(true);
    }

    const getProgressData = async() => {
        try {
            const response = await getProgress(Username, progress_id);
            const res = response[progress_id];
            setCompanyName(res[0]);
            setJobTitle(res[1]);
            var stagesTmp = [];
            for (var i = 0, l = res[2].length; i < l; i++) {
                stagesTmp.push({
                    Stage: res[2][i], 
                    date: res[3][i],
                    status: statusTable[res[4][i]],
                    color: getColorHover(res[4][i])
                })
            }
           
            setStages(stagesTmp);

        } catch (error) {
            if (error.response) {
                console.error('error:', error.response.data);
                console.error('Status code:', error.response.status);
                alert(`${error.response.data}`);
            } 
        }
        return;

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
                                {companyName}
                            </th>
                            <td className="px-6 py-3">
                                {jobTitle}
                            </td>
                            <td className="flex">
                                {stages.map((stage, index) => (
                                    <div key={index}
                                        className={`px-6 py-3 ${stage.color}`}
                                        onClick={() => handleClick(index , stage.status)}>
                                        {stage.date} {stage.Stage}
                                        <span className="text-xs"> ({stage.status})</span>
                                    </div>
                                ))}
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <NewStatusDialog 
            open={newStatusDialogOpen} 
            onClose={() => setNewStatusDialogOpen(false)} 
            Stages={stages} setNewStages={setStages}
        />
        <EditStatusDialog 
            open={editStatusDialogOpen} 
            onClose={() => setEditStatusDialogOpen(false)} 
            Stages={stages} 
            setNewStages={setStages}
            index={EditIndex}
            status = {EditStatus}
        />
        </>
    );
};
 
export default ProgressTracking;