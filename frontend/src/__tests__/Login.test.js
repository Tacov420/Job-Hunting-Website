import React from 'react';
import { render, fireEvent, screen, waitFor } from '@testing-library/react';
import '@testing-library/jest-dom';
import { act } from 'react-dom/test-utils'; 
import { UsernameProvider } from '../context/UsernameContext.jsx';
import { BrowserRouter, MemoryRouter } from "react-router-dom";
import MockAdapter from 'axios-mock-adapter';
import axios from 'axios';

import  Login  from '../pages/Login.jsx';
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
describe('test Login', () => {
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

    test('renders login form', () => {
        render(<UsernameProvider><BrowserRouter><Login /></BrowserRouter></UsernameProvider>);
        expect(screen.getByLabelText('Username')).toBeInTheDocument();
        expect(screen.getByLabelText('Password')).toBeInTheDocument();
        expect(screen.getByRole('button', { name: 'Login' })).toBeInTheDocument(); //expect(screen.getByRole("button")).toHaveTextContent("Login");
      });      
    it('should show alert for empty username and password', async () => {
        render(<UsernameProvider><BrowserRouter><Login /></BrowserRouter></UsernameProvider>);
        await act(async () => {
            fireEvent.click(screen.getByText('Login'));
        });
        await new Promise((resolve) => setTimeout(resolve, 0));
        expect(window.alert).toHaveBeenCalledWith('Please fill in all fields correctly.');
    });
    it('should navigate to home for successful login', async () => {
        //const mockedResponse = {status: 201};
        //axios.post.mockResolvedValue(mockedResponse);
        mock.onPost('/login').reply(201, { data: { status: 201 } });   
        render(<UsernameProvider><BrowserRouter><Login /></BrowserRouter></UsernameProvider>);
        await act(async () => {
            fireEvent.change(screen.getByLabelText('Username'), { target: { value: 'test0' } });
            fireEvent.change(screen.getByLabelText('Password'), { target: { value: 'test0' } });
            fireEvent.click(screen.getByRole('button', { name: 'Login' }));
            expect(screen.getByLabelText('Username').value).toBe('test0');
            expect(screen.getByLabelText('Password').value).toBe('test0'); 
        })
        await waitFor(() => {
            expect(mockNavigate).toHaveBeenCalledTimes(1);
            expect(mockNavigate).toHaveBeenCalledWith('/home');
        });
    });
    it('should link to register page when "Sign up" is clicked', async () => {
        render(<UsernameProvider><BrowserRouter><Login /></BrowserRouter></UsernameProvider>);
        await act(async () => {
            fireEvent.click(screen.getByText('Sign up'));
            expect(document.querySelector("a").getAttribute("href")).toBe("/register")})        
        });
    it("should navigate to verification if hasn't verified", async () => {
        mock.reset();
        const mockedError = { response: { status: 400, data: "Hasn't verified" } };
        mock.onPost('/login').reply(400, mockedError.response.data);
        render(
            <UsernameProvider><BrowserRouter><Login /></BrowserRouter></UsernameProvider>
        );
        await act(async () => {
            fireEvent.change(screen.getByLabelText('Username'), { target: { value: 'test' } });
            fireEvent.change(screen.getByLabelText('Password'), { target: { value: 'test' } });
            fireEvent.click(screen.getByRole('button', { name: 'Login' }));
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
        mock.onPost('/login').reply(400, mockedError.response.data);
        render(
            <UsernameProvider><BrowserRouter><Login /></BrowserRouter></UsernameProvider>
        );
        await act(async () => {
            fireEvent.change(screen.getByLabelText('Username'), { target: { value: 'test' } });
            fireEvent.change(screen.getByLabelText('Password'), { target: { value: 'test' } });
            fireEvent.click(screen.getByRole('button', { name: 'Login' }));
            expect(screen.getByLabelText('Username').value).toBe('test');
            expect(screen.getByLabelText('Password').value).toBe('test'); 
        })
        await waitFor(() => {
            expect(mockNavigate).toHaveBeenCalledTimes(1);
            expect(mockNavigate).toHaveBeenCalledWith('/preference');
        });
    });
});

