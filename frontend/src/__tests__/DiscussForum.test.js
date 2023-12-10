import React from 'react';
import { render, fireEvent, screen, waitFor } from '@testing-library/react';
import '@testing-library/jest-dom';
import { act } from 'react-dom/test-utils'; 
import { UsernameProvider } from '../context/UsernameContext.jsx';
import { BrowserRouter, MemoryRouter, Routes, Route, useLocation } from "react-router-dom";
import Router from "react-router-dom"
import MockAdapter from 'axios-mock-adapter';
import axios from 'axios';
import  DiscussForum  from '../pages/DiscussForum.jsx';
const mockNavigate = jest.fn();
jest.mock('react-router-dom', () => ({
    ...jest.requireActual('react-router-dom'),
    useNavigate: () => mockNavigate,
}));
jest.mock('axios');
window.alert = jest.fn();
/* jest.mock('react-router-dom', () => {
    const originalModule = jest.requireActual('react-router-dom');
  
    return {
      ...originalModule,
         useSearchParams: () => [new URLSearchParams({ category: 0 })],
    };
  }); */
jest.mock('axios', () => {
    return {
      ...(jest.requireActual('axios')),
      create: jest.fn().mockReturnValue(jest.requireActual('axios')),
    };
  });

/* jest.mock('react-router-dom', () => ({
...jest.requireActual('react-router-dom'),
useSearchParams: () => [new URLSearchParams({ category: '0' })],
}));
   */
const mock = new MockAdapter(axios);
const mock_getPost_data = {
    "0": [
        "Interview experience of SLEEPING",
        "2023-11-28 15:39:55",
        true
    ],
    "2": [
        "Interview experience of Software Engineer",
        "2023-11-28 16:12:46",
        false
    ]
}        
describe('test DiscussForum', () => {
/*     beforeEach(() => {
        mock.reset();
    });

    afterEach(() => {
        jest.restoreAllMocks()
        jest.clearAllMocks()
    }); */

    test('renders DiscussForum form', async () => {
/*         delete window.location;
        window.location = { search: '?category=0' };
        expect(window.location.search).toEqual('?category=0'); */     
        //mock.onGet('/post/list/test0/0').reply(201, { data: mock_getPost_data });   
        mock.onGet('/post/list/test0/0').reply(201, mock_getPost_data);   
        Object.defineProperty(window, 'location', {
            writable: true,
            value: { search: '?category=0' },
          });
/*           const location = {
            ...window.location,
            search: '?catrgory=0',
          };
          Object.defineProperty(window, 'location', {
            writable: true,
            value: location,
          }); */
     
        render(
            <UsernameProvider ><BrowserRouter><DiscussForum /></BrowserRouter></UsernameProvider>
        );
        await waitFor(() => {
            expect(screen.queryByText('Interview Experiences')).toBeInTheDocument();
            expect(screen.queryByText('Interview Questions')).toBeInTheDocument();
            expect(screen.queryByText('Employee Perks')).toBeInTheDocument();
            expect(screen.queryByText('Internal Referral')).toBeInTheDocument();
          });
      });      
      it('should show correct posts list', async () => {
                mock.onGet('/post/list/test0/0').reply(201, mock_getPost_data);   
                Object.defineProperty(window, 'location', {
                    writable: true,
                    value: { search: '?category=0' },
                  });
             
                render(
                    <UsernameProvider ><BrowserRouter><DiscussForum /></BrowserRouter></UsernameProvider>
                );
                waitFor(() => {
                    expect(screen.getByText('Interview experience of SLEEPING')).toBeInTheDocument();
                    expect(screen.getByText('Interview experience of Software Engineer')).toBeInTheDocument();
                  });
        });     
        it('should navigate to view post when clicking specific post', async () => {  
            mock.onGet('/post/list/test0/0').reply(201, mock_getPost_data);   
            Object.defineProperty(window, 'location', {
                writable: true,
                value: { search: '?category=0' },
              });
         
            render(
                <UsernameProvider ><BrowserRouter><DiscussForum /></BrowserRouter></UsernameProvider>
            );
            await waitFor(() => {
                expect(screen.getByText('Interview experience of SLEEPING')).toBeInTheDocument();
                expect(screen.getByText('Interview experience of Software Engineer')).toBeInTheDocument();
                act(async () => {
                    fireEvent.click(screen.getByText('Interview experience of SLEEPING'));
                    waitFor(() =>{
                      expect(mockNavigate).toHaveBeenCalledWith('/discuss_forum/view/0');
                    })
                });             
            });
    });       
});


