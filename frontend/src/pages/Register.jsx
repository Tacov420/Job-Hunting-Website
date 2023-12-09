import { useContext, useState, useRef, useEffect } from 'react';
import { UsernameContext } from '../context/UsernameContext';
import { createUser } from "../utils/client";
import { Link , useNavigate } from "react-router-dom";


const Register = () => {
	//const [username, setUsername] = useState('');
	//const [password, setPassword] = useState('');
	const usernameRef = useRef(null);
    const passwordRef = useRef(null);
	const confirmRef = useRef(null);
	const navigate = useNavigate();


	//const [confirmPassword, setConfirmPassword] = useState('');
	const [checkColor, setCheckColor] = useState('black');
	const { Username, updateUsername } = useContext(UsernameContext);
	
	useEffect(() => {
		console.log('Username changed:', Username);
	}, [Username]);

	const handleConfirmPasswordChange = () => {
		//const newPassword = event.target.value;
		const currentPassword = passwordRef.current?.value ?? "";
		const currentConfirm = confirmRef.current?.value ?? "";
		console.log(currentConfirm);
		console.log(currentPassword);

		if (currentConfirm === currentPassword) {
			setCheckColor('green');
			
		} else if (currentConfirm !== '' && currentConfirm !== currentPassword) {
			setCheckColor('red');
		} else {
			setCheckColor('black');
		}
		//setConfirmPassword(newPassword);
	};


	const handleContinue = async () => {

		const username = usernameRef.current?.value ?? "";
		const password = passwordRef.current?.value ?? "";
		const confirmPassword = confirmRef.current?.value ?? "";
		try {
			if (username !== '' && password !== '' && confirmPassword !== '' && confirmPassword === password) {
				
				const response = await createUser(username, password);
				console.log(response.data);
				if (response.status === 201) {
					updateUsername(username);
					navigate('/verification'); //跳到驗證信
				}
				else {
					alert(response.data);
					return;
				}
			} else {
				alert('Please fill in all fields correctly.');
				return;
			}
		} catch (error) {
			if (error.response) {
				if (error.response.status === 403){ //Username has already been registered
					alert(`failed: ${error.response.data}`);
					return;
				}
			}
		}
	};

	return (
		<>
		<section className="bg-gray-50">
			<div className="flex flex-col items-center justify-center px-6 py-8 mx-auto md:h-screen lg:py-0">
				<p className="flex items-center mb-6 text-2xl font-bold text-blue-800">
					Job hunting
				</p>
				<div className="w-full bg-white rounded-lg shadow md:mt-0 sm:max-w-md xl:p-0">
					<div className="p-6 space-y-4 md:space-y-6 sm:p-8">
						<h1 className="text-center text-xl font-bold text-gray-900 md:text-2xl ">
							Account
						</h1>
						<div className="space-y-4 md:space-y-6">
							<div>
								<label className="block mb-1 text-sm font-medium text-gray-900">Username</label>
								<input name="username" 
									className="bg-gray-200 text-gray-900 lg:text-lg rounded-lg w-full p-2.5" 
									ref={usernameRef}
								/>
							</div>
							<div>
								<label className="block mb-1 text-sm font-medium text-gray-900 ">Password</label>
								<input type="password" 
									className="bg-gray-200 text-gray-900 lg:text-lg rounded-lg w-full p-2.5"
									ref = {passwordRef}
								/>
							</div>
							<div>
								<label className="block mb-1 text-sm font-medium text-gray-900 ">Confirm Password</label>
								<input type="password" 
									className="bg-gray-200 text-gray-900 lg:text-lg rounded-lg w-full p-2.5"
									ref = {confirmRef}
									onChange={handleConfirmPasswordChange}
								/>
								<span style={{ color: checkColor}}>✔</span>
								{/*`text-${checkColor.toString()}-400`*/}
							</div>
							<button type="submit" 
								className="w-full text-white bg-gray-500 hover:bg-gray-700 font-medium rounded-md text-sm py-2.5"
								onClick={handleContinue}
							>
							Continue
							</button>
							<div>
							<p className="inline text-sm font-light text-gray-500">
								Already have an Account? 
							</p>
							<Link to="/">
								<p className="inline font-semibold text-blue-500 hover:text-blue-600"> Log in</p>
							</Link>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
		</>
	);
};

export default Register;

