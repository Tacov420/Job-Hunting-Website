import React, {useState , useRef} from "react";
import { Routes, Route, Link , useParams} from "react-router-dom";
import { MdEmail } from "react-icons/md";
import { RiDeleteBin5Fill } from "react-icons/ri";
import  ConfirmDeleteDialog  from '../components/ConfirmDeleteDialog';


const CompanyItem = ({id , CompanyName , email , onDelete , onChange}) => {
    //const dis
    //const currentPage = currentPage;
    const [notify, setNotify] = useState(email);
    const [confirmDialogOpen, setConfirmDialogOpen] = useState(false);

    const handleEmail = ()=>{
        //api
        setNotify(!notify);
    }


    return (
        <>
        <div className="flex flex-wrap justify-between w-full p-4 my-4 bg-gray-50 border border-gray-200 rounded-lg shadow hover:bg-gray-100">   
                <h5 className="mb-1 text-xl font-sm tracking-tight text-gray-500">{CompanyName}</h5>    
                <div className="flex md:order-2 space-x-3 md:space-x-3 rtl:space-x-reverse">
                    
                    <button type="button" className="flex">
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
        <ConfirmDeleteDialog open={confirmDialogOpen} onClose={()=>setConfirmDialogOpen(false)} onDelete={onDelete}/>
        </>
    );
};
 
export default CompanyItem;