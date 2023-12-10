import React from 'react';
import { render, fireEvent, screen, waitFor } from '@testing-library/react';
import '@testing-library/jest-dom';
import { act } from 'react-dom/test-utils'; 
import { UsernameProvider } from '../context/UsernameContext.jsx';
import { BrowserRouter, MemoryRouter } from "react-router-dom";
import MockAdapter from 'axios-mock-adapter';
import axios from 'axios';

import  Register  from '../pages/Register.jsx';
const mockNavigate = jest.fn();
jest.mock('react-router-dom', () => ({
    ...jest.requireActual('react-router-dom'),
    useNavigate: () => mockNavigate,
}));

jest.mock('axios');


/* jest.mock('../utils/client', () => ({
    getUser: jest.fn().mockResolvedValue({ status: 201, data: 'some data' }),
  }));  
 */


window.alert = jest.fn();
describe('test Register', () => {
    //const mock = new MockAdapter(axios);
    let mock;
    beforeEach(() => {
        mock = new MockAdapter(axios);
        //mock = new MockAdapter(client);
    });
    afterEach(() => {
        mock.restore();
    });

    test('renders register form', () => {
        render(<UsernameProvider><BrowserRouter><Register /></BrowserRouter></UsernameProvider>);
        //renderWithRouter(<Register />)
        expect(screen.getByLabelText('Username')).toBeInTheDocument();
        expect(screen.getByLabelText('Password')).toBeInTheDocument();
        expect(screen.getByLabelText('Confirm Password')).toBeInTheDocument();
        expect(screen.getByRole('button', { name: 'Continue' })).toBeInTheDocument(); //expect(screen.getByRole("button")).toHaveTextContent("Register");
      });      
    it('should show alert for empty username and password', async () => {
        render(<UsernameProvider><BrowserRouter><Register /></BrowserRouter></UsernameProvider>);
        await act(async () => {
            fireEvent.click(screen.getByText('Continue'));
        });
        await new Promise((resolve) => setTimeout(resolve, 0));
        expect(window.alert).toHaveBeenCalledWith('Please fill in all fields correctly.');
    });
    it('should navigate to verification for successful Register', async () => {
        const mockedResponse = {status: 201};
        axios.post.mockResolvedValue(mockedResponse);
        mock.onPost('/api/register/personalInfo').reply(201, { data: { status: 201 } });  
        render(<UsernameProvider><BrowserRouter><Register /></BrowserRouter></UsernameProvider>);
        await act(async () => {
            fireEvent.change(screen.getByLabelText('Username'), { target: { value: 'test' } });
            fireEvent.change(screen.getByLabelText('Password'), { target: { value: 'test' } });
            fireEvent.change(screen.getByLabelText('Confirm Password'), { target: { value: 'test' } });
            fireEvent.click(screen.getByRole('button', { name: 'Continue' }));
        });
        await waitFor(() => {
            expect(screen.getByLabelText('Username').value).toBe('test');
            expect(screen.getByLabelText('Password').value).toBe('test');
            expect(screen.getByLabelText('Confirm Password').value).toBe('test');
            expect(mockNavigate).toHaveBeenCalledTimes(1);
            expect(mockNavigate).toHaveBeenCalledWith('/verification');
        },); 
    });

});

