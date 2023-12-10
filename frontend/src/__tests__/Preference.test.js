import React from 'react';
import { render, fireEvent, screen, waitFor } from '@testing-library/react';
import '@testing-library/jest-dom';
import { act } from 'react-dom/test-utils'; 
import { UsernameProvider } from '../context/UsernameContext.jsx';
import { BrowserRouter, MemoryRouter } from "react-router-dom";
import MockAdapter from 'axios-mock-adapter';
import axios from 'axios';

import  Preference  from '../pages/Preference.jsx';
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
describe('test Preference', () => {
    beforeEach(() => {
        mock.reset();
    });

    afterEach(() => {
        jest.restoreAllMocks()
        jest.clearAllMocks()
    });
    test('renders preference form', () => {
        render(<UsernameProvider><BrowserRouter><Preference /></BrowserRouter></UsernameProvider>);
        expect(screen.getByText('Personal Information')).toBeInTheDocument();
        expect(screen.getByText('Preferences')).toBeInTheDocument();
        expect(screen.getByLabelText('Desired Job Title')).toBeInTheDocument();
        expect(screen.getByLabelText('Desired Job Location')).toBeInTheDocument();
        expect(screen.getByLabelText('Skills')).toBeInTheDocument();
        expect(screen.getByLabelText('Focus Companies')).toBeInTheDocument();
        expect(screen.getByRole('button', { name: 'Continue' })).toBeInTheDocument(); 

    });      
    it('should show correct userName', () => {
        render(<UsernameProvider><BrowserRouter><Preference /></BrowserRouter></UsernameProvider>);
        const inputElement = screen.getByLabelText('Name');  
        expect(inputElement).toBeInTheDocument();  
        expect(inputElement).toHaveValue('test0'); 
        //screen.debug();
    });      
    
    it('should have buttons for  each field', async () => {
        render(<UsernameProvider><BrowserRouter><Preference /></BrowserRouter></UsernameProvider>);
        expect(screen.getByTestId('jobButton')).toBeInTheDocument;
        expect(screen.getByTestId('locationButton')).toBeInTheDocument;
        expect(screen.getByTestId('companyButton')).toBeInTheDocument;
        expect(screen.getByTestId('skillsButton')).toBeInTheDocument;
        await act(async () => {
            //fireEvent.click(screen.getByRole('button', { name: "Desired Job Title" }));
            fireEvent.click(screen.getByTestId('jobButton'));
        })
/*         await waitFor(() => {
            expect(mockNavigate).toHaveBeenCalledTimes(1);
            expect(mockNavigate).toHaveBeenCalledWith('/home');
        }); */
    });


});

