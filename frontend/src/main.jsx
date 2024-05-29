import React from 'react'
import ReactDOM from 'react-dom/client'
import { UsernameProvider } from './context/UsernameContext.jsx';
import { BrowserRouter } from "react-router-dom";
import CssBaseline from "@mui/material/CssBaseline";


import App from './App.jsx'
import './index.css'

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <UsernameProvider>
      <CssBaseline />
      <BrowserRouter>
        <App />
      </BrowserRouter>
    </UsernameProvider>
  </React.StrictMode>,
)
