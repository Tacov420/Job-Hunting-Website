import React from 'react';
import { render, fireEvent, screen, waitFor } from '@testing-library/react';
import '@testing-library/jest-dom';
import { act } from 'react-dom/test-utils'; 
import { UsernameProvider } from '../context/UsernameContext.jsx';
import { BrowserRouter, MemoryRouter, Routes, Route, useLocation } from "react-router-dom";
import Router from "react-router-dom"
import MockAdapter from 'axios-mock-adapter';
import axios from 'axios';
import  Notification  from '../components/Notification.jsx';
import { getNotificationListFromResponseData } from '../components/Notification.jsx';
const mockNavigate = jest.fn();
jest.mock('react-router-dom', () => ({
    ...jest.requireActual('react-router-dom'),
    useNavigate: () => mockNavigate,
}));
jest.mock('axios');
window.alert = jest.fn();


const mock_getNotification_data = {
    "0": [
        "I'm going to hell.",
        true
    ],  
    "1": [
        "Hello",
        false
    ]
}

const expectedNotificationList = [
    {
        id: '1',
        content: 'Hello',
        isRead: false, 
    },
    {
        id: '0',
        content: "I'm going to hell.",
        isRead: true,
    },
];


describe('test Notification', () => {
    test('test getNotificationListFromResponseData function', async () => {
        const result = getNotificationListFromResponseData(mock_getNotification_data);
        expect(result).toEqual(expectedNotificationList);
    });     

});







