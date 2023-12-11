import React, {useState , useRef , useContext , useEffect} from "react";
import { UsernameContext } from '../context/UsernameContext';
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogTitle from "@mui/material/DialogTitle";
import {addProgress, getColor } from '../utils/client';


export default function NewJobDialog({ open, onClose , progresses, setNewProgress  }) {
    const CompanyRef = useRef(null);
    const JobTitleRef = useRef(null);
	const StageRef = useRef(null);
    const DateRef = useRef(null);
    const [StatusSelection , setStatusSelect] = useState('0');
    const { Username } = useContext(UsernameContext);
    const statusTable = ['Unknown', 'Accepted' , 'Rejected' , 'Quit'];


	const handleAddProgress = async () => {
        const companyName = CompanyRef.current?.value ?? "";
        const jobTitle = JobTitleRef.current?.value ?? "";
        const Stage = StageRef.current?.value ?? "";
        var date = DateRef.current?.value ?? "";

        if(companyName == "" || jobTitle == "" || Stage == "" || date == ""){
            alert('表單未填寫完全');
            return;
        }

        try{
            /*
            const response = await addProgress(Username, companyName , jobTitle , Stage , date , Number(StatusSelection) ); 
            if (response.status === 201){
                const newProgress = { 
                    id: response.data,  
                    companyName: companyName,
                    jobTitle: jobTitle,
                    latestStage: Stage,
                    latestDate: date,
                    latestStatus: statusTable[Number(StatusSelection)],
                    color: getColor(Number(StatusSelection)),
                }
                setNewProgress([...progresses, newProgress]);
            }*/        
            
            const newProgress = { 
                id: 3,  
                companyName: companyName,
                jobTitle: jobTitle,
                latestStage: Stage,
                latestDate: date,
                latestStatus: statusTable[Number(StatusSelection)],
                color: getColor(Number(StatusSelection)),
            }
            setNewProgress([...progresses, newProgress]);
        } catch (error) {
            if (error.response) {
                console.error('error:', error.response.data);
                console.error('Status code:', error.response.status);
                alert(`${error.response.data}`);
            } 
        }
        CompanyRef.current.value = "";
        JobTitleRef.current.value = "";
        StageRef.current.value = "";
        DateRef.current.value = "";
		setStatusSelect('0');
		onClose();
        return;
	};

    const handleChange = (e) => {
        setStatusSelect(e.target.value);
    };

    const handleCancel = () => {
        CompanyRef.current.value = "";
        JobTitleRef.current.value = "";
        StageRef.current.value = "";
        DateRef.current.value = "";
		setStatusSelect('0');
		onClose();
        return;
    }

  
	return (
		<Dialog open={open} onClose={onClose} fullWidth="true" maxWidth= "xs" id ="NewJobDialog">
		<DialogTitle sx={{fontWeight: 'bold' , fontSize: 22}}>Add Progress Tracking</DialogTitle>
		<DialogContent>
            <div className="space-y-4 md:space-y-6">
                
                <div>
                    <label className="block text-medium font-medium text-gray-900">Company</label>
                    <input 
                        className="bg-gray-200 text-gray-900 lg:text-lg rounded-lg w-full p-1.5"
                        ref = {CompanyRef}
                        id = "companyInput"
                    />
                </div>
                <div>
                    <label className="block text-medium font-medium text-gray-900">Job Title</label>
                    <input 
                        className="bg-gray-200 text-gray-900 lg:text-lg rounded-lg w-full p-1.5"
                        ref = {JobTitleRef}
                        id = "jobtitleInput"
                    />
                </div>
                <div>
                    <label className="block text-medium font-medium text-gray-900">Stage</label>
                    <input 
                        className="bg-gray-200 text-gray-900 lg:text-lg rounded-lg w-full p-1.5"
                        ref = {StageRef} 
                        id = "stageInput"
                    />
                </div>
                <div>
                    <label className="block text-medium font-medium text-gray-900">Date</label>
                    <input type="date"
                        className="bg-gray-200 text-gray-900 lg:text-lg rounded-lg w-full p-1.5"
                        ref = {DateRef} 
                        id = "DateInput"
                    />
                </div>
                <div>
                    <label className="block text-medium font-medium text-gray-900">Status</label>
                    <select 
                        className="bg-gray-200 text-gray-900 lg:text-lg rounded-lg w-full p-1.5"
                        defaultValue={StatusSelection} onChange={handleChange}
                        id="select"
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
                type="submit"
                className="px-5 bg-green-300 rounded-md font-sm text-gray-800 hover:bg-green-600"
                onClick={handleAddProgress}
            >
            Submit
            </button>
            <button 
                className="ml-1 px-5 bg-red-300 rounded-md font-sm text-gray-800 hover:bg-red-600"
                onClick={handleCancel}
                id = "cancel"
            >
            Cancel
            </button>
		</DialogActions>
		</Dialog>
	);
}
