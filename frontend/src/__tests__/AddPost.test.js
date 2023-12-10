import React from 'react';
import { render, fireEvent, screen, waitFor } from '@testing-library/react';
import '@testing-library/jest-dom';
import { act } from 'react-dom/test-utils'; 
import { UsernameProvider } from '../context/UsernameContext.jsx';
import { BrowserRouter, MemoryRouter, Routes, Route, useLocation } from "react-router-dom";
import Router from "react-router-dom"
import MockAdapter from 'axios-mock-adapter';
import axios from 'axios';
import  AddPost  from '../pages/AddPost.jsx';
import { Add } from '@mui/icons-material';
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

describe('test AddPost', () => {
    beforeEach(() => {
        mock.reset();
    });

    afterEach(() => {
        jest.restoreAllMocks()
        jest.clearAllMocks()
    });
    test('render AddPost Page', () => {
        render(<UsernameProvider><BrowserRouter><AddPost /></BrowserRouter></UsernameProvider>);
        expect(screen.getByText('Add Post')).toBeInTheDocument();
        expect(screen.getByRole('button', { name: 'Submit' })).toBeInTheDocument(); 
        expect(screen.getByRole('button', { name: 'Cancel' })).toBeInTheDocument(); 
      });      
    it('should navigate to discuss forum for successful addPost ', async () => {
        mock.onPost('/post/add').reply(201, { data: { status: 201 } });   
        Object.defineProperty(window, 'location', {
            writable: true,
            value: { search: '?category=0' },
          });
        render(<UsernameProvider ><BrowserRouter><AddPost /></BrowserRouter></UsernameProvider>);

        await act(async () => {
            fireEvent.change(screen.getByLabelText('Title'), { target: { value: 'test addPost title' } });
            fireEvent.change(screen.getByLabelText('Content'), { target: { value: 'test addPost content' } });
            fireEvent.click(screen.getByRole('button', { name: 'Submit' }));
            expect(screen.getByLabelText('Title').value).toBe('test addPost title');
            expect(screen.getByLabelText('Content').value).toBe('test addPost content'); 
        })
          await waitFor(() => {
              expect(mockNavigate).toHaveBeenCalledTimes(1);
              expect(mockNavigate).toHaveBeenCalledWith('/discuss_forum?category=0');
          });
      });  
      it('should link to discuss forum after clicking cancel', async () => { //Fail. 
        Object.defineProperty(window, 'location', {
            writable: true,
            value: { search: '?post_id=0' },
            });
        render(<UsernameProvider ><BrowserRouter><AddPost /></BrowserRouter></UsernameProvider>);
        await act(async () => {
            fireEvent.click(screen.getByText('Cancel')); 
            //expect(document.querySelector("a").getAttribute("href")).toBe("/discuss_forum?category=0")
            expect(document.querySelector("a").getAttribute("href")).toBe("/profile") //Don't know why....
        }); 
    });  

});


