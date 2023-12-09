
import React, { createContext, useContext, useState } from 'react';

const UsernameContext = createContext();

const UsernameProvider = ({ children }) => {
  const [Username, setUsername] = useState('');

  const updateUsername = (name) => {
    setUsername(name);
  };

  return (
    <UsernameContext.Provider value={{ Username, updateUsername }}>
    {children}
  </UsernameContext.Provider>
  )
};
const useUsername = () => {
  return useContext(UsernameContext);
};
export { UsernameProvider, useUsername, UsernameContext };
