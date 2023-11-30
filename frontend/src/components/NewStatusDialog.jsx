import { useRef } from "react";
import Button from "@mui/material/Button";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogTitle from "@mui/material/DialogTitle";


export default function NewStatusDialog({ open, onClose }) {

	const handleAddStatus = async () => {
		
	};

  
	return (
		<Dialog open={open} onClose={onClose} fullWidth="true" maxWidth= "xs" >
		<DialogTitle sx={{fontWeight: 'bold' , fontSize: 22}}>Add Status</DialogTitle>
		<DialogContent>

            <div className="space-y-4 md:space-y-6">
                <div>
                    <label className="block text-medium font-medium text-gray-900">Stage</label>
                    <input 
                        className="bg-gray-200 text-gray-900 lg:text-lg rounded-lg w-full p-1.5"
                        //ref = 
                    />
                </div>
                <div>
                    <label className="block text-medium font-medium text-gray-900">Date</label>
                    <input type="date"
                        className="bg-gray-200 text-gray-900 lg:text-lg rounded-lg w-full p-1.5"
                        //ref = 
                    />
                </div>
                <div>
                    <label className="block text-medium font-medium text-gray-900">Status</label>
                    <select id="countries" className="bg-gray-200 text-gray-900 lg:text-lg rounded-lg w-full p-1.5">
                        <option selected>stage 1</option>
                        <option value="stage2">stage 2</option>
                        <option value="stage3">stage 3</option>
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
                onClick={onClose}
            >
            Cancel
            </button>
		</DialogActions>
		</Dialog>
	);
}
