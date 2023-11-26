import { env } from "./env";
import axios from 'axios';


const client = axios.create({
    baseURL: env.VITE_API_URL,
});

//login
export function getUser(username , password){
	const data = {userName: username , password: password};
	return client.post("/login" , data );
};

//register
export function createUser(username , password){
	const data = {userName: username , password: password};
	return client.post("/register/personalInfo", data);
};

export function Verify(username , verificationCode){
	const data = {userName: username,verificationCode: verificationCode};
	return client.post("/register/verify", data);
};

export function createVerify(username , email){
	const data = {userName: username,email: email};
	return client.post("/register/sendVerification", data);
};

export function createPreferences(username , desiredJobsTitle , desiredJobsLocation , skills , companies){
	const data = {
		userName: username,
		desiredJobsTitle: desiredJobsTitle,
		desiredJobsLocation: desiredJobsLocation, 
		skills: skills, 
		companies: companies,
	};
	return client.post("/register/preference", data);
};




