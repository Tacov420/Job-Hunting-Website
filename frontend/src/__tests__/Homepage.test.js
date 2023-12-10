import React from 'react';
import { render, fireEvent, screen, waitFor } from '@testing-library/react';
import '@testing-library/jest-dom';
import { act } from 'react-dom/test-utils'; 
import { UsernameProvider } from '../context/UsernameContext.jsx';
import { BrowserRouter, MemoryRouter, Routes, Route, useLocation } from "react-router-dom";
import Router from "react-router-dom"
import MockAdapter from 'axios-mock-adapter';
import axios from 'axios';
import  HomePage  from '../pages/HomePage.jsx';
import { getCompanyListFromResponse, getJobListFromResponseDataForRecommendation, getJobListFromResponseDataForSearch } from '../pages/HomePage.jsx';
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
            "receiveEmail": 1
        }
    ],
    "2": [
        {
            "companyName": "ASUS",
            "receiveEmail": 1
        }
    ],
    "3": [
        {
            "companyName": "Appier",
            "receiveEmail": 1
        }
    ]
}

const expectedCompanies = ["Google", "Microsoft", "ASUS", "Appier"];
const mock_getJob_data =[
    {
    "_id":{
    "timestamp":1701985295,
    "date":"2023-12-07T21:41:35.000+00:00"
    },
    "company":"Crypto.com",
    "jobTitle":"Full-stack Developer",
    "level":"Mid-Senior level"
    },
    {
    "_id":{
    "timestamp":1701985295,
    "date":"2023-12-07T21:41:35.000+00:00"
    },
    "company":"Rêve Preparatory Charter School",
    "jobTitle":"Backend / Full Stack Engineer 後端/全端工程師",
    "level":"Mid-Senior level"
    }
]
    

const expectedJobs = [[
    {
        "company": "Crypto.com",
        "id": 0, 
        "isTrack": false, 
        "jobTitle": "Full-stack Developer",
        "level": "Mid-Senior level",
    }, 
    {
        "company": "Rêve Preparatory Charter School",
        "id": 1, 
        "isTrack": false, 
        "jobTitle": "Backend / Full Stack Engineer 後端/全端工程師",
        "level": "Mid-Senior level",
    }
], 2]

const expectedJobs2 = [[
    {
        "company": "Crypto.com",
        "id": 10, 
        "isTrack": false, 
        "jobTitle": "Full-stack Developer",
        "level": "Mid-Senior level",
    }, 
    {
        "company": "Rêve Preparatory Charter School",
        "id": 11, 
        "isTrack": false, 
        "jobTitle": "Backend / Full Stack Engineer 後端/全端工程師",
        "level": "Mid-Senior level",
    }
], 2]

const expectedJobs3 = [
    {
        "company": "Crypto.com",
        "id": 0, 
        "isTrack": false, 
        "jobTitle": "Full-stack Developer",
        "level": "Mid-Senior level",
    }, 
    {
        "company": "Rêve Preparatory Charter School",
        "id": 1, 
        "isTrack": false, 
        "jobTitle": "Backend / Full Stack Engineer 後端/全端工程師",
        "level": "Mid-Senior level",
    }
]

describe('test HomePage', () => {
    test('test getCompanyListFromResponse function', async () => {
        const result = getCompanyListFromResponse(mock_getCompany_data, 0);
        expect(result).toEqual(expectedCompanies);
    });       

    test('test getJobListFromResponseDataForRecommendation function for index==0', async () => {
        const result = getJobListFromResponseDataForRecommendation(mock_getJob_data, 0, expectedCompanies);
        expect(result).toEqual(expectedJobs);
    });     
    test('test getJobListFromResponseDataForRecommendation function for index!=0', async () => {
        const result = getJobListFromResponseDataForRecommendation(mock_getJob_data, 10, expectedCompanies);
        expect(result).toEqual(expectedJobs2);
    });     
    test('test getJobListFromResponseDataForSearch function for index!=0', async () => {
        const result = getJobListFromResponseDataForSearch(mock_getJob_data, expectedCompanies);
        expect(result).toEqual(expectedJobs3);
    });         

});


