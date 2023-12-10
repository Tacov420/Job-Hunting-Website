import React from 'react';
import { render, fireEvent, screen, waitFor } from '@testing-library/react';
import '@testing-library/jest-dom';
import { act } from 'react-dom/test-utils'; 
import { UsernameProvider } from '../context/UsernameContext.jsx';
import { BrowserRouter, MemoryRouter } from "react-router-dom";
import MockAdapter from 'axios-mock-adapter';
import axios from 'axios';

import  ProfilePage  from '../pages/ProfilePage.jsx';
const mockNavigate = jest.fn();
jest.mock('react-router-dom', () => ({
    ...jest.requireActual('react-router-dom'),
    useNavigate: () => mockNavigate,
}));
window.alert = jest.fn();

  
describe('test Profile', () => {
    test('renders profile page', () => {
        render(<UsernameProvider><BrowserRouter><ProfilePage /></BrowserRouter></UsernameProvider>);
        expect(screen.getByText('Preference')).toBeInTheDocument();
        expect(screen.getByText('Profile')).toBeInTheDocument();
    });      
});

