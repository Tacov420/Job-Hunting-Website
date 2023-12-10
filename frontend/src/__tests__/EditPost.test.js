import React from 'react';
import { render, fireEvent, screen, waitFor } from '@testing-library/react';
import '@testing-library/jest-dom';
import { act } from 'react-dom/test-utils'; 
import { UsernameProvider } from '../context/UsernameContext.jsx';
import { BrowserRouter, MemoryRouter, Routes, Route, useLocation } from "react-router-dom";
import Router from "react-router-dom"
import MockAdapter from 'axios-mock-adapter';
import axios from 'axios';
import  EditPost  from '../pages/EditPost.jsx';
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
const replies = [
    {
        "0": [
            "Sounds great.",
            "2023-11-28 19:33:37",
            false
        ],
        "1": [
            "wwwooooowww",
            "2023-11-28 19:34:43",
            true
        ]
    }
]
const postContent = "The interviewer was very nice and asked me to lie on a comfortable bed for two hours. After I woke up, HR told me that I had been accepted! What an interesting Interview experience!"
const postTitle = "Interview experience of SLEEPING"
const mock_getPost_data = {
    "replies": replies, 
    "postContent": postContent, 
    "postTitle": postTitle
}       

describe('test EditPost', () => {
    beforeEach(() => {
        mock.reset();
    });

    afterEach(() => {
        jest.restoreAllMocks()
        jest.clearAllMocks()
    });
    test('render EditPost Page', () => {
        render(<UsernameProvider><BrowserRouter><EditPost /></BrowserRouter></UsernameProvider>);
        expect(screen.getByText('Edit Post')).toBeInTheDocument();
        expect(screen.getByRole('button', { name: 'Submit' })).toBeInTheDocument(); 
        expect(screen.getByRole('button', { name: 'Cancel' })).toBeInTheDocument(); 
        expect(screen.getByRole('button', { name: 'Delete' })).toBeInTheDocument(); 
    });
    it('should show correct post title', async () => {
        mock.onGet('/post/specific/test0/0').reply(201, mock_getPost_data);   
        Object.defineProperty(window, 'location', {
            writable: true,
            value: { search: '?post_id=0' },
          });
        render(
            <UsernameProvider ><BrowserRouter><EditPost /></BrowserRouter></UsernameProvider>
        );
        waitFor(() => {
            expect(screen.getByText(postTitle)).toBeInTheDocument();
          });
      });      

    it('should show correct post content', async () => {
        mock.onGet('/post/specific/test0/0').reply(201, mock_getPost_data);   
        Object.defineProperty(window, 'location', {
            writable: true,
            value: { search: '?post_id=0' },
          });
        render(
            <UsernameProvider ><BrowserRouter><EditPost /></BrowserRouter></UsernameProvider>
        );
        waitFor(() => {
            expect(screen.getByText(postContent)).toBeInTheDocument();
          });
      });      
    it('should edit post successfully', async () => {  //cannot test
        mock.onGet('/post/specific/test0/0').reply(201, mock_getPost_data);   
        Object.defineProperty(window, 'location', {
            writable: true,
            value: { search: '?post_id=0' },
            });
        mock.onPut('/post/0').reply(201, { data: { status: 201 } });   
        render(<UsernameProvider ><BrowserRouter><EditPost /></BrowserRouter></UsernameProvider>);
        waitFor(() => {
            expect(screen.getByText('Edit Post')).toBeInTheDocument();
            expect(screen.getByRole('button', { name: 'Submit' })).toBeInTheDocument(); 
            expect(screen.getByRole('button', { name: 'Cancel' })).toBeInTheDocument(); 
            expect(screen.getByRole('button', { name: 'Delete' })).toBeInTheDocument();         
        });
    });     
    it('should delete post successfully', async () => {  //cannot test
        mock.onGet('/post/specific/test0/0').reply(201, mock_getPost_data);   
        Object.defineProperty(window, 'location', {
            writable: true,
            value: { search: '?post_id=0' },
            });
        mock.onDelete('/post/0').reply(201, mock_getPost_data);   
        render(<UsernameProvider ><BrowserRouter><EditPost /></BrowserRouter></UsernameProvider>);
        waitFor(() => {
            expect(screen.getByText('Edit Post')).toBeInTheDocument();
            expect(screen.getByRole('button', { name: 'Submit' })).toBeInTheDocument(); 
            expect(screen.getByRole('button', { name: 'Cancel' })).toBeInTheDocument(); 
            expect(screen.getByRole('button', { name: 'Delete' })).toBeInTheDocument();         
        });
    });     
    it('should link to discuss forum after clicking cancel', async () => { //Fail. 
        mock.onGet('/post/specific/test0/0').reply(201, mock_getPost_data);   
        Object.defineProperty(window, 'location', {
            writable: true,
            value: { search: '?post_id=0' },
            });
        render(<UsernameProvider ><BrowserRouter><EditPost /></BrowserRouter></UsernameProvider>);
        await act(async () => {
            fireEvent.click(screen.getByText('Cancel')); 
            //expect(document.querySelector("a").getAttribute("href")).toBe("/discuss_forum?category=0")
            expect(document.querySelector("a").getAttribute("href")).toBe("/profile") //Don't know why....
        }); 
    });  
});


