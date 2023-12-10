import React from 'react';
import { render, fireEvent, screen, waitFor } from '@testing-library/react';
import '@testing-library/jest-dom';
import { act } from 'react-dom/test-utils'; 
import { UsernameProvider } from '../context/UsernameContext.jsx';
import { BrowserRouter, MemoryRouter, Routes, Route, useLocation } from "react-router-dom";
import Router from "react-router-dom"
import MockAdapter from 'axios-mock-adapter';
import axios from 'axios';
import PostItem from '../components/Post.jsx';

const postId = '0';
const postTitle = 'Sample Post';
const categoryId = '0';
const editMode = true;

describe('PostItem', () => {
    test('renders PostItem component with title', () => {
        render(
            <UsernameProvider>
                <BrowserRouter>
                    <PostItem id={postId} title={postTitle} edit={editMode} categoryId={categoryId} />
                </BrowserRouter>
            </UsernameProvider>
        );
        expect(screen.getByText(postTitle)).toBeInTheDocument();
    });
    it('should link to editPost Page if clicking edit button', () => { //Fail.
        render(
            <UsernameProvider>
                <BrowserRouter>
                    <PostItem id={postId} title={postTitle} edit={editMode} categoryId={categoryId} />
                </BrowserRouter>
            </UsernameProvider>
        );
        const editButton = screen.getByText('edit / delete');
        fireEvent.click(editButton);
        expect(document.querySelector("a").getAttribute("href")).toBe("/view/0?category=0") //Don't know why....
    });
});
