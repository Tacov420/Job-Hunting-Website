import React from 'react';
import { render, fireEvent, screen, waitFor } from '@testing-library/react';
import '@testing-library/jest-dom';
import { act } from 'react-dom/test-utils'; 
import { UsernameProvider } from '../context/UsernameContext.jsx';
import { BrowserRouter, MemoryRouter, Routes, Route, useLocation } from "react-router-dom";
import Router from "react-router-dom"
import MockAdapter from 'axios-mock-adapter';
import axios from 'axios';
import  ProgressTracking  from '../pages/ProgressTracking.jsx';
import { getProgressListFromResponse } from '../pages/ProgressTracking.jsx';
const mockNavigate = jest.fn();
jest.mock('react-router-dom', () => ({
    ...jest.requireActual('react-router-dom'),
    useNavigate: () => mockNavigate,
}));
jest.mock('axios');
window.alert = jest.fn();


const mock_getProgress_data = {
    "0": [
        "MS",
        "777",
        [
            "aaahhhhhh"
        ],
        [
            "2022-01-16"
        ],
        [
            3
        ]
    ],
    "1": [
        "Google",
        "Backend Engineer",
        [
            "一面"
        ],
        [
            "2106-01-16"
        ],
        [
            1
        ]
    ]
}

const expectedProgressList = [
    {
        "color": "bg-slate-500 text-slate-200",
        "companyName": "MS",
        "id": "0",
        "jobTitle": "777",
        "latestDate": "2022-01-16",
        "latestStage": "aaahhhhhh",
        "latestStatus": "Quit",
    }, 
    {
        "color": "bg-green-400 text-green-800",
        "companyName": "Google",
        "id": "1",
        "jobTitle": "Backend Engineer",
        "latestDate": "2106-01-16",
        "latestStage": "一面",
        "latestStatus": "Accepted",
    }, 
];

describe('test ProgressTracking', () => {
     test('renders ProgressTracking page', () => {
        render(<UsernameProvider><BrowserRouter><ProgressTracking /></BrowserRouter></UsernameProvider>);
        expect(screen.getByText('Company')).toBeInTheDocument();
        expect(screen.getByText('Job Title')).toBeInTheDocument();
        expect(screen.getByText('Latest Status')).toBeInTheDocument();
        expect(screen.getByText('Delete')).toBeInTheDocument();
        expect(screen.getByRole('button', { name: 'Add Job' })).toBeInTheDocument(); 

    });   
    test('test getProgressListFromResponse function', async () => {
        const result = getProgressListFromResponse(mock_getProgress_data);
        expect(result).toEqual(expectedProgressList);
    });       
});


