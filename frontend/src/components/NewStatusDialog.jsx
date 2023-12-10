import React, {useState , useRef , useContext } from "react";
import { UsernameContext } from '../context/UsernameContext';
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogTitle from "@mui/material/DialogTitle";
import { addStage,  getColorHover  } from "../utils/client";
import { useParams} from "react-router-dom";

 
export default function NewStatusDialog({ open, onClose, Stages, setNewStages }) {
    const { progress_id } = useParams(); 
	const StageRef = useRef(null);
    const DateRef = useRef(null);
    const [StatusSelection , setStatusSelect] = useState('0');
    const { Username } = useContext(UsernameContext);
    const statusTable = ['Unknown', 'Accepted' , 'Rejected' , 'Quit'];

    const handleChange = (e) => {
        setStatusSelect(e.target.value);
    };

	const handleAddStatus = async () => {
        const Stage = StageRef.current?.value ?? "";
        var date = DateRef.current?.value ?? "";

        if(Stage == "" || date == ""){
            alert('表單未填寫完全');
            return;
        }
        try{
            //console.log(StatusSelection);
            const response = await addStage(Username, progress_id , Stage , date , Number(StatusSelection) ); 
            if (response.status === 201){
                const newStage = { 
                    Stage: Stage, 
                    date:  date,
                    status: statusTable[Number(StatusSelection)],
                    color: getColorHover(Number(StatusSelection)),
                }
                setNewStages([...Stages, newStage]);
            }        
        } catch (error) {
            if (error.response) {
                console.error('error:', error.response.data);
                console.error('Status code:', error.response.status);
                alert(`${error.response.data}`);
            } 
        }
        StageRef.current.value = "";
        DateRef.current.value = "";
		setStatusSelect('0');
		onClose();
        return;
	};

    const handleCancel = () => {
        StageRef.current.value = "";
        DateRef.current.value = "";
		setStatusSelect('0');
		onClose();
        return;
    }

  
	return (
		<Dialog open={open} onClose={onClose} fullWidth="true" maxWidth= "xs" >
		<DialogTitle sx={{fontWeight: 'bold' , fontSize: 22}}>Add Status</DialogTitle>
		<DialogContent>

            <div className="space-y-4 md:space-y-6">
                <div>
                    <label className="block text-medium font-medium text-gray-900">Stage</label>
                    <input 
                        className="bg-gray-200 text-gray-900 lg:text-lg rounded-lg w-full p-1.5"
                        ref = {StageRef}
                    />
                </div>
                <div>
                    <label className="block text-medium font-medium text-gray-900">Date</label>
                    <input type="date"
                        className="bg-gray-200 text-gray-900 lg:text-lg rounded-lg w-full p-1.5"
                        ref = {DateRef}
                    />
                </div>
                <div>
                    <label className="block text-medium font-medium text-gray-900">Status</label>
                    <select 
                        className="bg-gray-200 text-gray-900 lg:text-lg rounded-lg w-full p-1.5"
                        defaultValue={StatusSelection} onChange={handleChange}
                    >
                        <option key ="0" value="0" selected>Unknown</option>
                        <option key ="1" value="1">Accepted</option>
                        <option key ="2" value="2">Rejected</option>
                        <option key ="3" value="3">Quit</option>
                    </select>
                </div>
            </div>
		</DialogContent>
		<DialogActions>
            <button 
                className="px-5 bg-green-300 rounded-md font-sm text-gray-800 hover:bg-green-600"
                onClick={handleAddStatus}
            >
            Submit
            </button>
            <button 
                className="ml-1 px-5 bg-red-300 rounded-md font-sm text-gray-800 hover:bg-red-600"
                onClick={handleCancel}
            >
            Cancel
            </button>
		</DialogActions>
		</Dialog>
	);
}
