import React, { useContext, useState, useEffect, useRef } from 'react';
import { UsernameContext } from '../context/UsernameContext';
import { createPreferences } from "../utils/client";
import { Link , useNavigate } from "react-router-dom";
import { IoMdAddCircle } from "react-icons/io";

const Preference = () => {
	const { Username } = useContext(UsernameContext);
	const [desiredJobsTitle, setDesiredJobsTitle] = useState([]);
	const [desiredJobsLocation, setDesiredJobsLocation] = useState([]);
	const [companies, setCompanies] = useState([]);
	const [skills, setSkills] = useState([]);
	
	const jobTitleRef = useRef(null);
	const LocationRef = useRef(null);
	const CompanyRef = useRef(null);
	const SkillRef = useRef(null);
	const navigate = useNavigate();
	console.log(Username);

	const handleDialogConfirm = (selectedBox) => {
		let updatedList = [];

		switch (selectedBox) {
		case 'desiredJobsTitle':
			const title = jobTitleRef.current?.value ?? "";
			if(title == '') return;
			updatedList = [...desiredJobsTitle, title];
			setDesiredJobsTitle(updatedList);
			jobTitleRef.current.value = '';
			break;
		case 'desiredJobsLocation':
			const Location = LocationRef.current?.value ?? "";
			if(Location == '') return;
			updatedList = [...desiredJobsLocation, Location];
			setDesiredJobsLocation(updatedList);
			LocationRef.current.value = '';
			break;
		case 'companies':
			const Company = CompanyRef.current?.value ?? "";
			if(Company == '') return;
			updatedList = [...companies, Company];
			setCompanies(updatedList);
			CompanyRef.current.value = '';
			break;
		case 'skills':
			const skill = SkillRef.current?.value ?? "";
			if(skill == '') return;
			updatedList = [...skills, skill];
			setSkills(updatedList);
			SkillRef.current.value = '';
			break;
		default:
			break;
		}
		
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
					Job Hunting
				</p>
				<div className="w-full rounded-lg md:mt-0 sm:max-w-lg">
					<div className="p-6 space-y-4 md:space-y-6 sm:p-8">
						<h1 className="text-left text-xl font-bold text-gray-900 md:text-2xl ">Personal Information</h1>
						<div className="space-y-4 md:space-y-6">
							<div>
								<label className="block mb-1 text-lg font-medium text-gray-900">Name</label>
								<input name="username" 
								className="bg-gray-200 text-gray-700 lg:text-lg rounded-lg w-full p-2.5" 
								defaultValue={Username} readOnly/>              
							</div>
						</div>

						<h1 className="text-left text-xl font-bold text-gray-900 md:text-2xl ">Preferences</h1>
						<div className="space-y-4 md:space-y-6">
							<div>
								<label className="block mb-1 text-lg font-medium text-gray-900">Desired Job Title</label>	
								<div class="relative w-full">
									<input 
										className="block bg-gray-200 text-gray-900 lg:text-lg rounded-lg w-full py-2.5 pl-2.5 pr-8 mb-2"
										ref = {jobTitleRef}
									/>	
									<button
										className="absolute items-center top-0 end-0 p-1.5 h-full text-sm font-medium text-gray-500 rounded-e-lg" 
										onClick={() => handleDialogConfirm('desiredJobsTitle')} >
										<IoMdAddCircle size={23} className=''/>
									</button>
								</div>
		
								{desiredJobsTitle.map((title, index) => (
									<span key={index} className="text-medium bg-blue-200 my-3 mx-1 p-0.5 rounded-md">
										{title}
										<button className = "ml-1" onClick={() => handleRemoveItem('desiredJobsTitle', index)}>×</button>
									</span>
								))}
								
							</div>
							<div>
								<label className="block mb-1 text-lg font-medium text-gray-900">Desired Job Location</label>	
								<div class="relative w-full">
									<input 
										className="block bg-gray-200 text-gray-900 lg:text-lg rounded-lg w-full py-2.5 pl-2.5 pr-8 mb-2"
										ref = {LocationRef}
									/>	
									<button
										className="absolute items-center top-0 end-0 p-1.5 h-full text-sm font-medium text-gray-500 rounded-e-lg" 
										onClick={() => handleDialogConfirm('desiredJobsLocation')} >
										<IoMdAddCircle size={23} className=''/>
									</button>
								</div>
		
								{desiredJobsLocation.map((location, index) => (
									<span key={index} className="text-medium bg-blue-200 my-3 mx-1 p-0.5 rounded-md">
										{location}
										<button className = "ml-1.5" onClick={() => handleRemoveItem('desiredJobsLocation', index)}>×</button>
									</span>
								))}								
							</div>

							<div>
								<label className="block mb-1 text-lg font-medium text-gray-900">Focus Companies</label>	
								<div class="relative w-full">
									<input 
										className="block bg-gray-200 text-gray-900 lg:text-lg rounded-lg w-full py-2.5 pl-2.5 pr-8 mb-2"
										ref = {CompanyRef}
									/>	
									<button
										className="absolute items-center top-0 end-0 p-1.5 h-full text-sm font-medium text-gray-500 rounded-e-lg" 
										onClick={() => handleDialogConfirm('companies')} >
										<IoMdAddCircle size={23} className=''/>
									</button>
								</div>
		
								{companies.map((company, index) => (
									<span key={index} className="text-medium bg-blue-200 my-3 mx-1 p-0.5 rounded-md">
										{company}
										<button className = "ml-1" onClick={() => handleRemoveItem('companies', index)}>×</button>
									</span>
								))}									
							</div>
							<div>
								<label className="block mb-1 text-lg font-medium text-gray-900">Skills</label>	
								<div class="relative w-full">
									<input 
										className="block bg-gray-200 text-gray-900 lg:text-lg rounded-lg w-full py-2.5 pl-2.5 pr-8 mb-2"
										ref = {SkillRef}
									/>	
									<button
										className="absolute items-center top-0 end-0 p-1.5 h-full text-sm font-medium text-gray-500 rounded-e-lg" 
										onClick={() => handleDialogConfirm('skills')} >
										<IoMdAddCircle size={23} className=''/>
									</button>
								</div>
		
								{skills.map((skill, index) => (
									<span key={index} className="text-medium bg-blue-200 my-3 mx-1 p-0.5 rounded-md">
										{skill}
										<button className = "ml-1" onClick={() => handleRemoveItem('skills', index)}>×</button>
									</span>
								))}	
							</div>
							
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
