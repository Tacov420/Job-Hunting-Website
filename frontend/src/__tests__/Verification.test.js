import React from 'react';
import { render, fireEvent, screen, waitFor } from '@testing-library/react';
import '@testing-library/jest-dom';
import { act } from 'react-dom/test-utils'; 
import { UsernameProvider } from '../context/UsernameContext.jsx';
import { BrowserRouter, MemoryRouter } from "react-router-dom";
import MockAdapter from 'axios-mock-adapter';
import axios from 'axios';

import  Verification  from '../pages/Verification.jsx';
const mockNavigate = jest.fn();
jest.mock('react-router-dom', () => ({
    ...jest.requireActual('react-router-dom'),
    useNavigate: () => mockNavigate,
}));
jest.mock('axios');
window.alert = jest.fn();

jest.mock('axios', () => {
    return {
      ...(jest.requireActual('axios')),
      create: jest.fn().mockReturnValue(jest.requireActual('axios')),
    };
  });
  
const mock = new MockAdapter(axios);
describe('test Verification', () => {
    //let mock;
    //let client;
    beforeEach(() => {
        mock.reset();
        //mock = new MockAdapter(axios);
        //client = axios.create();
        //mock = new MockAdapter(client);    
    });

    afterEach(() => {
        //mock.restore();
        //mock.reset();
        jest.restoreAllMocks()
        jest.clearAllMocks()
    });

    test('renders Verification form', () => {
        render(<UsernameProvider><BrowserRouter><Verification /></BrowserRouter></UsernameProvider>);
        expect(screen.getByText('Account')).toBeInTheDocument();
        expect(screen.getByText('Email Address')).toBeInTheDocument();
        expect(screen.getByRole('button', { name: 'Send Verification' })).toBeInTheDocument();
        expect(screen.getByRole('button', { name: 'Continue' })).toBeInTheDocument(); 
      });      
    it('should show alert for empty email', async () => {
        render(<UsernameProvider><BrowserRouter><Verification /></BrowserRouter></UsernameProvider>);
        await act(async () => {
            fireEvent.click(screen.getByText('Send Verification'));
        });
        await new Promise((resolve) => setTimeout(resolve, 0));
        expect(window.alert).toHaveBeenCalledWith('Please fill in all fields correctly.');
    });
    it('should show alert for empty verification code', async () => {
        render(<UsernameProvider><BrowserRouter><Verification /></BrowserRouter></UsernameProvider>);
        await act(async () => {
            fireEvent.click(screen.getByText('Continue'));
        });
        await new Promise((resolve) => setTimeout(resolve, 0));
        expect(window.alert).toHaveBeenCalledWith('Please fill in all fields correctly.');
    });
});

