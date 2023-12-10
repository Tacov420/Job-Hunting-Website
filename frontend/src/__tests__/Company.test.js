import React from 'react';
import { render, fireEvent, screen, waitFor } from '@testing-library/react';
import '@testing-library/jest-dom';
import { act } from 'react-dom/test-utils'; 
import { UsernameProvider } from '../context/UsernameContext.jsx';
import { BrowserRouter, MemoryRouter, Routes, Route, useLocation } from "react-router-dom";
import Router from "react-router-dom"
import MockAdapter from 'axios-mock-adapter';
import axios from 'axios';
import CompanyItem from '../components/Company.jsx';

const id = '0';
const CompanyName = 'Microsoft';
const email = true;
const onDelete = true;

describe('CompanyItem', () => {
    test('renders CompanyItem component with CompanyName', () => {
        render(
            <UsernameProvider>
                <BrowserRouter>
                    <CompanyItem id={id} CompanyName={CompanyName} email={email} onDelete={onDelete} />
                </BrowserRouter>
            </UsernameProvider>
        );
        expect(screen.getByText(CompanyName)).toBeInTheDocument();
    });

});
