import { env } from "./env";
import axios from 'axios';


const client = axios.create({
    baseURL: env.VITE_API_URL,
});

//login
export function getUser(username, password){
	const data = {userName: username, password: password};
	return client.post("/login", data);
};


//register
export function createUser(username, password){
	const data = {userName: username, password: password};
	return client.post("/register/personalInfo", data);
};

export function Verify(username, verificationCode){
	const data = {userName: username, verificationCode: verificationCode};
	return client.post("/register/verify", data);
};

export function createVerify(username, email){
	const data = {userName: username, email: email};
	//console.log(data);
	return client.post("/register/sendVerification", data);
};

export function createPreferences(username, desiredJobsTitle, desiredJobsLocation, skills, companies){
	const data = {
		userName: username,
		desiredJobsTitle: desiredJobsTitle,
		desiredJobsLocation: desiredJobsLocation, 
		skills: skills, 
		companies: companies,
	};
	return client.post("/register/preference", data);
};

export function getposts(username, catagory){
	const data = {userName: username, catagory: catagory};
	//console.log(data);
	return client.post("/register/sendVerification", data); //change!!
};


// Profile
export function getPersonalInfo(username){
	return client.get(`/profile/${username}`);
};

export function updatePassword(username, newPassword){
	const data = {newPassword: newPassword, confirmPassword: newPassword};
	return client.put(`/profile/${username}`, data);
};

export function getPreference(username){
	return client.get(`/profile/preference/${username}`);
};