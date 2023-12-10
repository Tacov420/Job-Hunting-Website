import React, { useContext , useRef } from 'react';
import { UsernameContext } from '../context/UsernameContext';
import { Verify , createVerify } from "../utils/client";
import { Link , useNavigate } from "react-router-dom";


const Verification = () => {
	const emailRef = useRef(null);
    const vericodeRef = useRef(null);
  	const { Username } = useContext(UsernameContext);
	const navigate = useNavigate();


	const handleContinue = async () => {
		const email = emailRef.current?.value ?? "";
		const verificationCode = vericodeRef.current?.value ?? "";
		console.log('email: ', email , 'verificationCode:' , verificationCode);  
		if (email == '' || verificationCode == '') {
			alert('Please fill in all fields correctly.');
			return;
		}
		
		try {	
			console.log('username:', Username , 'verificationCode:' , verificationCode);
			const response = await Verify(Username , verificationCode);
			console.log(response.data);
			navigate('/preference'); 
	
		} catch (error) {
			console.error('Verification failed:', error.response ? error.response.data : error.message);
			alert('Verification failed. Please try again.');
			return;
		}
	};
  
	const handleSendVerification = async () => {
		const email = emailRef.current?.value ?? "";
		console.log('email: ', email);  
		if (email == '') {
			alert('Please fill in all fields correctly.');
			return;
		}
		try {
			const response = await createVerify(Username , email);
			console.log(response.data);
			alert('Sent verification code successfully.');
		} catch (error) {
			if (error.response) {
				if (error.response.status === 403){ 
					alert(`failed: ${error.response.data}`);
				}
			}
		}
		return;
  	};
  
	return (
		<>
		<section className="bg-gray-50">
			<div className="flex flex-col items-center justify-center px-6 py-8 mx-auto md:h-screen lg:py-0">
				<p className="flex items-center mb-6 text-2xl font-bold text-blue-800">
					Job Hunting
				</p>
				<div className="w-full bg-white rounded-lg shadow dark:border md:mt-0 sm:max-w-md xl:p-0">
					<div className="p-6 space-y-4 md:space-y-6 sm:p-8">
						<h1 className="text-center text-xl font-bold text-gray-900 md:text-2xl ">
							Account
						</h1>
						<div className="space-y-4 md:space-y-6">
							<div>
								<label className="block mb-1 text-sm font-medium text-gray-900">Email Address</label>
								<input name="email" 
									id="email"
									className="bg-gray-200 text-gray-900 lg:text-lg rounded-lg w-full p-2.5" 
									ref = {emailRef}
								/>
								<button type="button" 
									id="sendVerification"
									className="mt-2 px-2 py-1.5 text-white bg-green-500 hover:bg-green-600 rounded-lg text-sm"
									onClick={handleSendVerification}
								>Send Verification
								</button>
							</div>
							<div>
								<label className="block mb-1 text-sm font-medium text-gray-900 ">Verification Code</label>
								<input  
									id="verificationCode"
									className="bg-gray-200 text-gray-900 lg:text-lg rounded-lg w-full p-2.5"
									ref = {vericodeRef}
								/>
							</div>
							<button type="submit" 
								id="continue"
								className="w-full text-white bg-gray-500 hover:bg-gray-700 font-medium rounded-md text-sm py-2.5"
								onClick={handleContinue}
							>Continue
							</button>
						</div>
					</div>
				</div>
			</div>
		</section>
		</>
	);


};

export default Verification;


