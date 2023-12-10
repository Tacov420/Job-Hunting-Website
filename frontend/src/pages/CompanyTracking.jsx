import React, {useState , useRef , useContext , useEffect} from "react";
import { UsernameContext } from '../context/UsernameContext';
import  TitleBar  from '../components/TitleBar';
import  CompanyItem  from '../components/Company';
import { BiChevronDown } from "react-icons/bi";
import { AiOutlineSearch } from "react-icons/ai";
import {getCompanies ,  deleteCompany , addCompany , getAllCompany} from '../utils/client';

const CompanyTracking = () => {
    const [companies, setCompanies] = useState([]);
    const [select , setSelect] = useState('');
    const [companyPool, setCompanyPool] = useState(null);
	const [inputValue, setInputValue] = useState("");
	const [open, setOpen] = useState(false);
    const { Username } = useContext(UsernameContext);

    useEffect(() => {    
        getCompanyList();
        getCompanyPool();
    }, []); 


    const getCompanyList = async () => {
        try{
            const response = await getCompanies(Username);
            const keys = Object.keys(response);
            const companyList = keys.map(key => ({
                id: key,
                companyName: response[key][0]['companyName'],
                isTrack: response[key][0]['receiveEmail'],
            }));
            setCompanies(companyList);
        }catch (error) {
            if (error.response) {
                alert(`${error.response.data}`);
            } 
        }
        return;
    }

    const getCompanyPool = async() => {
		try{
			const response = await getAllCompany(Username);
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

    const handleAddCompany = async(id) =>{
        //setSelect(companyName);
        if(select == '')
            return;
        try{
            console.log(select);
            const response = await addCompany(Username, select); 
            if (response.status === 201){
                const newCompany = { 
                    id: response.data,  
                    companyName: select,
                    isTrack: true,
                }
                setCompanies([...companies, newCompany]);
                setCompanyPool((preCompanies) => preCompanies.filter((company) => company.companyName !== select));
            }
        } catch (error) {
            if (error.response) {
                console.error('error:', error.response.data);
                console.error('Status code:', error.response.status);
                alert(`${error.response.data}`);
            } 
        }
        setOpen(false);
		setInputValue("");
        return;
    }

    const handleDeleteCompany = async (companyId) => {
        try {
            const response = await deleteCompany(Username, companyId);
            if (response.status === 201) {
                setCompanies((preCompanies) => preCompanies.filter((company) => company.id !== companyId));
            }
            
        } catch (error) {
            console.error('Error deleting company:', error);
        }
    };


    return (
        <>
        <TitleBar display={true} currentPage={'company tracking'}/>
        <div className='items-center mx-24 px-10 mt-6'>
            <div className="flex flex-row ">
                <div className="w-1/2 bg-gray-100 rounded-xl mx-5">
                    <div className="mx-5">
                    <h2 className="text-xl font-bold text-gray-500 py-3">
                        My Company Tracking List
                    </h2>
                    {companies.map(company => (
                        <CompanyItem 
                            id={company.id} 
                            CompanyName={company.companyName}
                            email={company.isTrack}  
                            onDelete = {handleDeleteCompany}
                        />
                    ))} 
                    </div>
                </div>
                <div className="w-1/2">
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
                                (company.companyName.toLowerCase().match(inputValue.toLowerCase()) && !company.isTrack)? 
                                    "block": "hidden"
                                }`}
                                onClick={() => {
                                    setSelect(company?.companyName);
                                    handleAddCompany();
                                }}
                            >
                                {company?.companyName}
                            </li>
                            
                            
                            ))}
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        
        </>
    );
};
 
export default CompanyTracking;