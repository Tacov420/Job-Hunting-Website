import React from 'react';
import { render, fireEvent, screen, waitFor } from '@testing-library/react';
import '@testing-library/jest-dom';
import { act } from 'react-dom/test-utils'; 
import { UsernameProvider } from '../context/UsernameContext.jsx';
import { BrowserRouter, MemoryRouter, Routes, Route, useLocation } from "react-router-dom";
import Router from "react-router-dom"
import MockAdapter from 'axios-mock-adapter';
import axios from 'axios';
import  CompanyTracking  from '../pages/CompanyTracking.jsx';
import { getCompanyListFromResponse, getCompanyPoolFromResponse } from '../pages/CompanyTracking.jsx';
const mockNavigate = jest.fn();
jest.mock('react-router-dom', () => ({
    ...jest.requireActual('react-router-dom'),
    useNavigate: () => mockNavigate,
}));
jest.mock('axios');
window.alert = jest.fn();


const mock_getCompany_data = {
    "0": [
        {
            "companyName": "Google",
            "receiveEmail": 1
        }
    ],
    "1": [
        {
            "companyName": "Microsoft",
            "receiveEmail": 0
        }
    ],
}
const expectedCompanyList = [
    {
        id: '0',
        companyName: 'Google',
        isTrack: 1,
    },
    {
        id: '1',
        companyName: 'Microsoft',
        isTrack: 0,
    },
];

const mock_getCompanyPool_data = {
    "companies": [
        [
            "Synpulse8",
            false
        ],
        [
            "Synology",
            false
        ],
        [
            "Cisco",
            false
        ],
        [
            "ASUS",
            true
        ],
    ]
}

const expectedCompanyPool = [
    {"companyName": "ASUS", "isTrack": true}, 
    {"companyName": "Cisco", "isTrack": false}, 
    {"companyName": "Synology", "isTrack": false}, 
    {"companyName": "Synpulse8", "isTrack": false}, 
]
describe('test CompanyTracking', () => {
    test('renders CompanyTracking page', () => {
        render(<UsernameProvider><BrowserRouter><CompanyTracking /></BrowserRouter></UsernameProvider>);
        expect(screen.getByText('My Company Tracking List')).toBeInTheDocument();
        expect(screen.getByText('Select and Add To Company Tracking')).toBeInTheDocument();
    });      
    test('test getCompanyListFromResponse function', async () => {
        const result = getCompanyListFromResponse(mock_getCompany_data);
        expect(result).toEqual(expectedCompanyList);
    });     
    test('test getCompanyPoolFromResponse function', async () => {
        const result = getCompanyPoolFromResponse(mock_getCompanyPool_data);
        expect(result).toEqual(expectedCompanyPool);
    });       
});







