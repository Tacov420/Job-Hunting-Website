import React, { useContext, useState, useRef, useEffect } from 'react';
import { UsernameContext } from '../context/UsernameContext';
import { getUser } from "../utils/client";
import { Link , useNavigate } from "react-router-dom";

const Login = () => {
    const usernameRef = useRef(null);
    const passwordRef = useRef(null);
    const { Username, updateUsername } = useContext(UsernameContext);
	const navigate = useNavigate()

	useEffect(() => {
		console.log('Username changed:', Username);
	}, [Username]);

	const handleLogin = async () => {
		const username = usernameRef.current.value;
		const password = passwordRef.current.value;
		if (username === '' || password === ''){
			alert('Please fill in all fields correctly.');
			return;
		}
		try {
			updateUsername(username);
			const response = await getUser(username, password);
		
			if (response.status === 201) {
				console.log(response.data);
				navigate('/home');
			} 
		} catch (error) {
			if (error.response) {
				console.error('Login error:', error.response.data);
				console.error('Status code:', error.response.status);
				if (error.response.status === 403){
					alert(`Login failed: ${error.response.data}`);
					return;
				}
				else if (error.response.status === 400){
					if (error.response.data === "Hasn't verified"){
						navigate('/verification'); //跳到驗證信
					}
					else if (error.response.data === "Hasn't filled in preference"){
						navigate('/preference'); //跳到填preference
					}
				}
				else{
					return;
					//error.response.status === 500
				}
			} 
		};
	};
  

	return (
		<section className="bg-gray-50">
			<div className="flex flex-col items-center justify-center px-6 py-8 mx-auto md:h-screen lg:py-0">
				<p className="flex items-center mb-6 text-3xl font-bold text-blue-800">
					Job Hunting
				</p>
				<div className="w-full bg-white rounded-lg shadow md:mt-0 sm:max-w-md xl:p-0">
					<div className="p-6 space-y-4 md:space-y-6 sm:p-8">
						<h1 className="text-center text-xl font-medium text-gray-900 md:text-2xl ">
							Create an account or sign in
						</h1>
						<div className="space-y-4 md:space-y-6">
							<div>
								<label className="block mb-1 text-sm font-medium text-gray-900">Username</label>
								<input name="username" 
									className="bg-gray-200 text-gray-900 lg:text-lg rounded-lg w-full p-1.5"
									ref = {usernameRef}
								/>
							</div>
							<div>
								<label className="block mb-1 text-sm font-medium text-gray-900 ">Password</label>
								<input type="password" 
									className="bg-gray-200 text-gray-900 lg:text-lg rounded-lg w-full p-1.5"
									ref = {passwordRef}
								/>
							</div>
							<button type="submit" 
								className="w-full text-white bg-gray-500 hover:bg-gray-700 font-medium rounded-md text-sm py-2.5"
								onClick={handleLogin}>		
							Login
							</button>
							<div>
								<p className="inline text-sm font-light text-gray-500">
									Don't have an account yet? 
								</p>
								<Link to="/register">
								<p className="inline text-sm font-semibold text-blue-500 hover:text-blue-800"> Sign up</p>
								</Link>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
	);
};

export default Login;
