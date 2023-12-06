import React, { useEffect, useState, useContext , useRef} from "react";
import { BiChevronDown } from "react-icons/bi";
import { AiOutlineSearch } from "react-icons/ai";
import { UsernameContext } from '../context/UsernameContext';
import {getAllCompany} from '../utils/client';


const Selector = (onAdd , onSelect ) => {
	const { Username } = useContext(UsernameContext);
	const [companyPool, setCompanyPool] = useState(null);

	const [inputValue, setInputValue] = useState("");
	//const [selected, setSelected] = useState("");
	const [open, setOpen] = useState(false);

	useEffect(() => {
		getCompanyPool();
	}, []);

	const getCompanyPool = async() => {
		try{
			//const response = await getAllCompany(encodeURIComponent(Username));
			const response = await getAllCompany('test0');
			const companies = response['companies'].map(company => ({
                companyName: company[0],
                isTrack: company[1],
            }));
			// sort by name
			companies.sort((a, b) => {
				const nameA = a.companyName.toUpperCase(); // ignore upper and lowercase
				const nameB = b.companyName.toUpperCase(); // ignore upper and lowercase
				if (nameA < nameB)	return -1;
				if (nameA > nameB) 	return 1;
				return 0;  // names must be equal
			});
			setCompanyPool(companies);
		}catch (error) {
			if (error.response) {
				alert(`${error.response.data}`);
			} 
		}
		return;
	}

	

	return (
		<div className="w-full font-medium h-10">
		<div
			onClick={() => setOpen(!open)}
			className="bg-blue-100 w-full p-2 flex items-center justify-between rounded-xl"
		>
			Select and Add To Company Tracking 
			<BiChevronDown size={20} className={`${open && "rotate-180"}`} />
		</div>
		<ul className={`bg-gray-200 mt-3 overflow-y-auto ${open ? "max-h-80" : "max-h-0"} `}>
			<div className="flex items-center px-2 m-1.5 sticky top-0 bg-white rounded-lg">
			<AiOutlineSearch size={18} className="text-gray-700" />
			<input
				type="text"
				value={inputValue}
				placeholder="Enter company name"
				className="w-full placeholder:text-gray-400 p-2 outline-none"
				onChange={(e) => setInputValue(e.target.value.toLowerCase())}
			/>
			</div>
			{companyPool?.map((company) => (
			<li
				key={company?.companyName}
				className={`p-2 text-sm bg-gray-100 text-gray-500 hover:bg-sky-600 hover:text-white
				${
				(company.companyName.toLowerCase().match(inputValue.toLowerCase()))? 
					"block": "hidden"
				}`}
				onClick={() => {
					onSelect(company?.companyName);
					onAdd();
					setOpen(false);
					setInputValue("");
				}}
			>
				{company?.companyName}
			</li>
			))}
		</ul>
		</div>
	);
};

export default Selector;