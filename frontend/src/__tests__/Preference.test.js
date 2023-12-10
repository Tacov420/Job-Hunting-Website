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
    /*
    it('should link to register page when "Sign up" is clicked', async () => {
        render(<UsernameProvider><BrowserRouter><Preference /></BrowserRouter></UsernameProvider>);
        await act(async () => {
            fireEvent.click(screen.getByText('Sign up'));
            expect(document.querySelector("a").getAttribute("href")).toBe("/register")})        
        });
    it("should navigate to verification if hasn't verified", async () => {
        mock.reset();
        const mockedError = { response: { status: 400, data: "Hasn't verified" } };
        mock.onPost('/Preference').reply(400, mockedError.response.data);
        render(
            <UsernameProvider><BrowserRouter><Preference /></BrowserRouter></UsernameProvider>
        );
        await act(async () => {
            fireEvent.change(screen.getByLabelText('Username'), { target: { value: 'test' } });
            fireEvent.change(screen.getByLabelText('Password'), { target: { value: 'test' } });
            fireEvent.click(screen.getByRole('button', { name: 'Preference' }));
            expect(screen.getByLabelText('Username').value).toBe('test');
            expect(screen.getByLabelText('Password').value).toBe('test'); 
        })
        await waitFor(() => {
            expect(mockNavigate).toHaveBeenCalledTimes(1);
            expect(mockNavigate).toHaveBeenCalledWith('/verification');
        });
    });
    it("should navigate to preference if hasn't filled in preference", async () => {
        mock.reset();
        const mockedError = { response: { status: 400, data: "Hasn't filled in preference" } };
        mock.onPost('/Preference').reply(400, mockedError.response.data);
        render(
            <UsernameProvider><BrowserRouter><Preference /></BrowserRouter></UsernameProvider>
        );
        await act(async () => {
            fireEvent.change(screen.getByLabelText('Username'), { target: { value: 'test' } });
            fireEvent.change(screen.getByLabelText('Password'), { target: { value: 'test' } });
            fireEvent.click(screen.getByRole('button', { name: 'Preference' }));
            expect(screen.getByLabelText('Username').value).toBe('test');
            expect(screen.getByLabelText('Password').value).toBe('test'); 
        })
        await waitFor(() => {
            expect(mockNavigate).toHaveBeenCalledTimes(1);
            expect(mockNavigate).toHaveBeenCalledWith('/preference');
        });
    });*/
});

