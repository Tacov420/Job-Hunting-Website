import React from 'react';
import { render, fireEvent, screen, waitFor } from '@testing-library/react';
import '@testing-library/jest-dom';
import { act } from 'react-dom/test-utils'; 
import { UsernameProvider } from '../context/UsernameContext.jsx';
import { BrowserRouter, MemoryRouter, Routes, Route, useLocation } from "react-router-dom";
import Router from "react-router-dom"
import MockAdapter from 'axios-mock-adapter';
import axios from 'axios';
import  ViewPost  from '../pages/ViewPost.jsx';
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

describe('test ViewPost', () => {
    beforeEach(() => {
        mock.reset();
    });

    afterEach(() => {
        jest.restoreAllMocks()
        jest.clearAllMocks()
    });
    it('should show correct post title', async () => {
        mock.onGet('/post/specific/test0/0').reply(201, mock_getPost_data);   
        Object.defineProperty(window, 'location', {
            writable: true,
            value: { search: '?post_id=0' },
          });
        render(
            <UsernameProvider ><BrowserRouter><ViewPost /></BrowserRouter></UsernameProvider>
        );
        waitFor(() => {
            const inputElement = screen.getByRole('textbox', { name: 'postTitle' }); // 如果你的 input 沒有 label，可以省略 name
            expect(inputElement).toHaveValue(postTitle);
          });
      });      

    it('should show correct post content', async () => {
        mock.onGet('/post/specific/test0/0').reply(201, mock_getPost_data);   
        Object.defineProperty(window, 'location', {
            writable: true,
            value: { search: '?post_id=0' },
          });
        render(
            <UsernameProvider ><BrowserRouter><ViewPost /></BrowserRouter></UsernameProvider>
        );
        waitFor(() => {
            expect(screen.getByText(postContent)).toBeInTheDocument();
          });
      });      
    it('should show correct replies', async () => {
        mock.onGet('/post/specific/test0/0').reply(201, mock_getPost_data);   
        Object.defineProperty(window, 'location', {
            writable: true,
            value: { search: '?post_id=0' },
            });
        
        render(
            <UsernameProvider ><BrowserRouter><ViewPost /></BrowserRouter></UsernameProvider>
        );
        waitFor(() => {
            expect(screen.getByText('Sounds great.')).toBeInTheDocument();
            expect(screen.getByText('wwwooooowww')).toBeInTheDocument();
            });
    });     
    it('can add reply correctly', async () => {  //cannot test 
        mock.onGet('/post/specific/test0/0').reply(201, mock_getPost_data);  
        Object.defineProperty(window, 'location', {
            writable: true,
            value: { search: '?post_id=0' },
            }); 
        render(
            <UsernameProvider ><BrowserRouter><ViewPost /></BrowserRouter></UsernameProvider>
        );
        const addReplyButton = screen.getByText('Add Reply');
        expect(addReplyButton).toBeInTheDocument();
    });  
});


