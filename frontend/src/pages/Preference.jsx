import React, { useContext, useState, useEffect, useRef } from 'react';
import { UsernameContext } from '../context/UsernameContext';
import { createPreferences } from "../utils/client";
import { Link , useNavigate } from "react-router-dom";


const Preference = () => {
	const { Username } = useContext(UsernameContext);
	const [desiredJobsTitle, setDesiredJobsTitle] = useState([]);
	const [desiredJobsLocation, setDesiredJobsLocation] = useState([]);
	const [companies, setCompanies] = useState([]);
	const [skills, setSkills] = useState([]);
	const [isDialogOpen, setIsDialogOpen] = useState(false);
	const [tempInput, setTempInput] = useState('');
	const [selectedBox, setSelectedBox] = useState(null);
	const inputRef = useRef(null);
	const navigate = useNavigate();


	const handleOpenDialog = (box) => {
		setIsDialogOpen(true);
		setSelectedBox(box);
	};

	useEffect(() => {
		if (isDialogOpen && inputRef.current) {
			inputRef.current.focus();
		}
	}, [isDialogOpen]); 

	const handleCloseDialog = () => {
		setIsDialogOpen(false);
		setTempInput('');
	};

	const handleDialogInputChange = (e) => {
		setTempInput(e.target.value);
	};  
	
	const handleDialogConfirm = () => {
		let updatedList = [];

		switch (selectedBox) {
		case 'desiredJobsTitle':
			updatedList = [...desiredJobsTitle, tempInput];
			setDesiredJobsTitle(updatedList);
			break;
		case 'desiredJobsLocation':
			updatedList = [...desiredJobsLocation, tempInput];
			setDesiredJobsLocation(updatedList);
			break;
		case 'companies':
			updatedList = [...companies, tempInput];
			setCompanies(updatedList);
			break;
		case 'skills':
			updatedList = [...skills, tempInput];
			setSkills(updatedList);
			break;
		default:
			break;
		}
		
		handleCloseDialog();
	};

	const handleRemoveItem = (box, index) => {
		let updatedList = [];

		switch (box) {
			case 'desiredJobsTitle':
				updatedList = desiredJobsTitle.filter((_, i) => i !== index);
				setDesiredJobsTitle(updatedList);
				break;
			case 'desiredJobsLocation':
				updatedList = desiredJobsLocation.filter((_, i) => i !== index);
				setDesiredJobsLocation(updatedList);
				break;
			case 'companies':
				updatedList = companies.filter((_, i) => i !== index);
				setCompanies(updatedList);
				break;
			case 'skills':
				updatedList = skills.filter((_, i) => i !== index);
				setSkills(updatedList);
				break;
			default:
				break;
			}    
		};
	
	const handleContinue = async () => {
		try {
			const data = {
				userName: Username,
				desiredJobsTitle: desiredJobsTitle,
				desiredJobsLocation: desiredJobsLocation, 
				skills: skills, 
				companies: companies,
			};
			const response = await createPreferences(Username , desiredJobsTitle , desiredJobsLocation , skills , companies);

			if (response.status === 201) {
				console.log(response.data);
				navigate('/home'); 
			}
			else {
				alert(response.data);
			}
		} catch (error) {
			if (error.response) {
				console.error('error:', error.response.data);
				console.error('Status code:', error.response.status);
				alert(`${error.response.data}`);
			} 
		}
		return;
	
	};

	return (
		<>
		<section className="bg-gray-50">
			<div className="flex flex-col items-center justify-center px-6 py-8 mx-auto lg:py-0">
				<p className="flex items-center mb-6 text-2xl font-bold text-blue-800">
					Job hunting
				</p>
				<div className="w-full rounded-lg md:mt-0 sm:max-w-lg">
					<div className="p-6 space-y-4 md:space-y-6 sm:p-8">
						<h1 className="text-left text-xl font-bold text-gray-900 md:text-2xl ">Personal Information</h1>
						<div className="space-y-4 md:space-y-6">
							<div>
								<label className="block mb-1 text-sm font-medium text-gray-900">Name</label>
								<input name="username" 
								className="bg-gray-200 text-gray-900 lg:text-lg rounded-lg w-full p-2.5" />              
							</div>
						</div>

						<h1 className="text-left text-xl font-bold text-gray-900 md:text-2xl ">Preferences</h1>
						<div className="space-y-4 md:space-y-6">
							<div>
								<label className="block mb-1 text-sm font-medium text-gray-900">Desired Job Title</label>								
								<input name="username" className="inline-block bg-gray-200 text-gray-900 lg:text-lg rounded-lg w-full p-2.5"/>		
								{desiredJobsTitle.map((title, index) => (
									<div key={index} className="tag">
										{title}
										<button onClick={() => handleRemoveItem('desiredJobsTitle', index)}>×</button>
									</div>
								))}
								<button onClick={() => handleOpenDialog('desiredJobsTitle')}>+</button>
							</div>
							<div>
								<label className="block mb-1 text-sm font-medium text-gray-900 ">Desired Job Location</label>
								<input type="password" className="bg-gray-200 text-gray-900 lg:text-lg rounded-lg w-full p-2.5"/>
								{desiredJobsLocation.map((location, index) => (
								<div key={index} className="tag">
									{location}
									<button onClick={() => handleRemoveItem('desiredJobsLocation', index)}>×</button>
								</div>
								))}
								<button onClick={() => handleOpenDialog('desiredJobsLocation')}>+</button>
							</div>
							<div>
								<label className="block mb-1 text-sm font-medium text-gray-900 ">Skills</label>
								<input type="password" className="bg-gray-200 text-gray-900 lg:text-lg rounded-lg w-full p-2.5"/>
								{companies.map((company, index) => (
									<div key={index} className="tag">
										{company}
										<button onClick={() => handleRemoveItem('companies', index)}>×</button>
									</div>
								))}
								<button onClick={() => handleOpenDialog('companies')}>+</button>
							</div>
							{isDialogOpen && (
								<div>
								<label>Enter Text: </label>
								<input ref={inputRef} type="text" value={tempInput} onChange={handleDialogInputChange} />
								<button onClick={handleDialogConfirm}>Confirm</button>
								<button onClick={handleCloseDialog}>Cancel</button>
								</div>
							)}
							<button type="submit" 
								className="w-full text-white bg-gray-500 hover:bg-gray-700 font-medium rounded-md text-sm py-2.5"
								onClick={handleContinue}
							>Continue</button>
						</div>
					</div>
				</div>
			</div>
		</section>
		</>
	);
};

export default Preference;
