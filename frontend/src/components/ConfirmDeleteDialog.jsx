import Dialog from "@mui/material/Dialog";
import DialogContent from "@mui/material/DialogContent";
import { RiDeleteBin6Fill } from "react-icons/ri";


export default function ConfirmDeleteDialog({ open, onClose , onDelete}) {

	return (
		<Dialog open={open} fullWidth="true" maxWidth= "xs" >
		<DialogContent>     
            <div className="relative p-4 text-center bg-white rounded-lg sm:p-5">
                <RiDeleteBin6Fill className="text-gray-400 w-11 h-11 mb-3.5 mx-auto"/>
                <p className="mb-4 text-gray-500 text-xl font-bold">Confirm Delete?</p>
                <div className="flex justify-center items-center space-x-4">
                    <button
                        className="py-2 px-3 text-medium font-medium text-gray-500 bg-white rounded-lg border border-gray-300 hover:bg-gray-100 hover:text-gray-900"
                        onClick={onClose}>
                        No, cancel
                    </button>
                    <button
                        className="py-2 px-3 text-medium font-medium text-center text-white bg-red-500 rounded-lg hover:bg-red-400"
                        onClick={onDelete}>
                        Yes, I'm sure
                    </button>
                </div>
            </div>
		</DialogContent>
		</Dialog>
	);
}
