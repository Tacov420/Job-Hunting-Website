import React, {useState , useRef , useContext, useEffect} from "react";
import { Routes, Route, Link , useParams , useNavigate} from "react-router-dom";
import { UsernameContext } from '../context/UsernameContext';
import { MdEmail } from "react-icons/md";
import { RiDeleteBin5Fill } from "react-icons/ri";
import Dialog from "@mui/material/Dialog";
import DialogContent from "@mui/material/DialogContent";
import { RiDeleteBin6Fill } from "react-icons/ri";
import {changeTracking} from '../utils/client';


const CompanyItem = ({id , CompanyName , email , onDelete  }) => {
    const { Username } = useContext(UsernameContext);

    const [notify, setNotify] = useState(email);
    const [loading, setLoading] = useState(false);
    const [confirmDialogOpen, setConfirmDialogOpen] = useState(false);

    useEffect(() => {
		console.log('Username:', Username);
	}, [Username]);

    const handleEmail = async()=>{     
        setLoading(true);
        setNotify(!notify);
        try{
            await changeTracking(Username , id);
        }catch (error) {
            console.error('Error deleting reply:', error);
        }
        setLoading(false);
    }

    const handleDeleteCompany = async() => {
        try {
            await onDelete(id);
            setConfirmDialogOpen(false);
        
        } catch (error) {
            console.error('Error deleting reply:', error);
        }
    };

    return (
        <>
        <div className="flex justify-between w-full p-4 my-4 bg-white border border-gray-200 rounded-lg shadow hover:bg-gray-50">   
            <h5 className="mb-1 text-xl font-sm tracking-tight text-gray-500">{CompanyName}</h5>    
            <div className="flex md:order-2 space-x-3 md:space-x-3 rtl:space-x-reverse">
                
                <button type="button" className="flex" disabled={loading}>
                <MdEmail size={30} 
                    className={`flex text-medium font-medium text-center ${notify? "text-green-600": "text-gray-600"}`}
                    onClick={handleEmail}
                />
                </button> 
                <button type="button" 
                    className="flex"
                    onClick={()=>setConfirmDialogOpen(true)}
                >
                <RiDeleteBin5Fill size={25} className="text-red-600 hover:text-red-700"/>
                </button>
            </div>
        </div>

        <Dialog open={confirmDialogOpen} fullWidth="true" maxWidth= "xs" >
		<DialogContent>     
            <div className="relative p-4 text-center bg-white rounded-lg sm:p-5">
                <RiDeleteBin6Fill className="text-gray-400 w-11 h-11 mb-3.5 mx-auto"/>
                <p className="mb-4 text-gray-500 text-xl font-bold">Confirm Delete?</p>
                <div className="flex justify-center items-center space-x-4">
                    <button
                        className="py-2 px-3 text-medium font-medium text-gray-500 bg-white rounded-lg border border-gray-300 hover:bg-gray-100 hover:text-gray-900"
                        onClick={()=>setConfirmDialogOpen(false)}>
                        No, cancel
                    </button>
                    <button
                        className="py-2 px-3 text-medium font-medium text-center text-white bg-red-500 rounded-lg hover:bg-red-400"
                        onClick={handleDeleteCompany}>
                        Yes, I'm sure
                    </button>
                </div>
            </div>
		</DialogContent>
		</Dialog>
        </>
    );
};
 
export default CompanyItem;