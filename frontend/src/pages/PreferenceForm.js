import React, { useContext, useState, useEffect, useRef } from 'react';
import { UsernameContext } from '../context/UsernameContext';
import axios from 'axios';

const PreferenceForm = ({onContinue}) => {
  const backendApiUrl = process.env.REACT_APP_BACKEND_API_URL;
  const { Username } = useContext(UsernameContext);
  const [desiredJobsTitle, setDesiredJobsTitle] = useState([]);
  const [desiredJobsLocation, setDesiredJobsLocation] = useState([]);
  const [companies, setCompanies] = useState([]);
  const [skills, setSkills] = useState([]);
  const [isDialogOpen, setIsDialogOpen] = useState(false);
  const [tempInput, setTempInput] = useState('');
  const [selectedBox, setSelectedBox] = useState(null);
  const inputRef = useRef(null);

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
      const response = await axios.post(backendApiUrl+'/register/preference', data, {
        headers: {
          'Content-Type': 'application/json',
        },
      });

      if (response.status === 201) {
        console.log(response.data);
        onContinue();
      }
      else {
        alert(response.data)
      }
    } catch (error) {
      if (error.response) {
        console.error('error:', error.response.data);
        console.error('Status code:', error.response.status);
        alert(`${error.response.data}`)
      } 
    }
  
  };

  return (
    <div>
      <h2>Preferences</h2>

      <div>
        <label>Desired Job Title:</label>
        {desiredJobsTitle.map((title, index) => (
          <div key={index} className="tag">
            {title}
            <button onClick={() => handleRemoveItem('desiredJobsTitle', index)}>×</button>
          </div>
        ))}
        <button onClick={() => handleOpenDialog('desiredJobsTitle')}>+</button>
      </div>

      <div>
        <label>Desired Job Location:</label>
        {desiredJobsLocation.map((location, index) => (
          <div key={index} className="tag">
            {location}
            <button onClick={() => handleRemoveItem('desiredJobsLocation', index)}>×</button>
          </div>
        ))}
        <button onClick={() => handleOpenDialog('desiredJobsLocation')}>+</button>
      </div>

      <div>
        <label>Focus Companies:</label>
        {companies.map((company, index) => (
          <div key={index} className="tag">
            {company}
            <button onClick={() => handleRemoveItem('companies', index)}>×</button>
          </div>
        ))}
        <button onClick={() => handleOpenDialog('companies')}>+</button>
      </div>

      <div>
        <label>Skills:</label>
        {skills.map((skill, index) => (
          <div key={index} className="tag">
            {skill}
            <button onClick={() => handleRemoveItem('skills', index)}>×</button>
          </div>
        ))}
        <button onClick={() => handleOpenDialog('skills')}>+</button>
      </div>

      {isDialogOpen && (
        <div>
          <label>Enter Text: </label>
          <input ref={inputRef} type="text" value={tempInput} onChange={handleDialogInputChange} />
          <button onClick={handleDialogConfirm}>Confirm</button>
          <button onClick={handleCloseDialog}>Cancel</button>
        </div>
      )}

      <button type="button" onClick={handleContinue}>
        Continue
      </button>
    </div>
  );
};

export default PreferenceForm;
