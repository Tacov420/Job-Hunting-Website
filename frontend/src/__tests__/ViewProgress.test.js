import React from 'react';
import { render, fireEvent, screen, waitFor } from '@testing-library/react';
import '@testing-library/jest-dom';
import { act } from 'react-dom/test-utils'; 
import { UsernameProvider } from '../context/UsernameContext.jsx';
import { BrowserRouter, MemoryRouter, Routes, Route, useLocation } from "react-router-dom";
import Router from "react-router-dom"
import MockAdapter from 'axios-mock-adapter';
import axios from 'axios';
import  ViewProgress  from '../pages/ViewProgress.jsx';
import { getProgressFromResponse } from '../pages/ViewProgress.jsx';
const mockNavigate = jest.fn();
jest.mock('react-router-dom', () => ({
    ...jest.requireActual('react-router-dom'),
    useNavigate: () => mockNavigate,
}));
jest.mock('axios');
window.alert = jest.fn();


const mock_getProgress_data =[
    "MS",
    "777",
    [
        "aaahhhhhh", "hello", "kkk", 
    ],
    [
        "2022-01-16", "2022-02-11", "2022-02-27", 
    ],
    [
        1, 0, 2, 
    ]
]


const expectedProgress = [
    {
        "Stage": "aaahhhhhh",
        "color": "bg-green-400 text-green-800 hover:bg-green-500",
        "date": "2022-01-16",
        "status": "Accepted", 
    },
    {
        "Stage": "hello",
        "color": "bg-amber-200 text-amber-800 hover:bg-amber-300",
        "date": "2022-02-11",
        "status": "Unknown", 
    },
    {
        "Stage": "kkk",
        "color": "bg-red-300 text-red-800 hover:bg-red-400",
        "date": "2022-02-27",
        "status": "Rejected", 
    }
];

describe('test ViewProgress', () => {
    test('renders ViewProgress page', () => {
        render(<UsernameProvider><BrowserRouter><ViewProgress /></BrowserRouter></UsernameProvider>);
        expect(screen.getByText('Company')).toBeInTheDocument();
        expect(screen.getByText('Job Title')).toBeInTheDocument();
        expect(screen.getByRole('button', { name: 'Add Status' })).toBeInTheDocument(); 
    });    
    test('test getProgressFromResponse function', async () => {
        const result = getProgressFromResponse(mock_getProgress_data);
        expect(result).toEqual(expectedProgress);
    });       
});


