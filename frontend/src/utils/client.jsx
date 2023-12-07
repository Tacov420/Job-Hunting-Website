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

//forum
export function getposts(username , categoryId){
	//const data = post_list[categoryId];
	//return data;
	const categoryIdInt = parseInt(categoryId, 10);
	if (typeof categoryIdInt !== 'number' && !Number.isInteger(categoryIdInt) && categoryIdInt <= 0) {
		console.log('categoryId must be a int');
	}		
	return client.get(`/post/list/${username}/${categoryIdInt}`,  {
		headers: {
			'Accept': 'application/json',
			'Content-Type': 'application/json',
		},
	})
	.then(response => {
		return response.data;
	})
	.catch(error => {
		throw error.response.data;
	});
};

export function getpost(username , postId){
	const postIdInt = parseInt(postId, 10);
	return client.get(`/post/specific/${username}/${postIdInt}`,  {
		headers: {
			'Accept': 'application/json',
			'Content-Type': 'application/json',
		},
	})
	.then(response => {
		return response.data;
	})
	.catch(error => {
		throw error.response.data;
	});
};

export function addPost(username, categoryId, postTitle, postContent){
	const data = {userName: username , categoryId: categoryId, postTitle: postTitle, postContent: postContent};
	return client.post("/post/add", data);
};
export function editPost(username, postId, postTitle, postContent){
	const postIdInt = parseInt(postId, 10);
	const data = {userName: username , postTitle: postTitle, postContent: postContent};
	return client.put(`/post/${postIdInt}`, data);
};
export function deletePost(userName, postId){
	const postIdInt = parseInt(postId, 10);
    const config = {
        data: { userName: userName }, 
    };	return client.delete(`/post/${postIdInt}`, config);
};


export function addReply(username, postId, replyContent){
	const data = {userName: username , postId: postId, replyContent: replyContent};
	return client.post("/reply/add", data);
};
export function deleteReply(userName, replyId){
    const config = {
        data: { userName: userName }, 
    };	return client.delete(`/reply/${replyId}`, config);
};
export function editReply(username, replyId, replyContent){
	const data = {userName: username, replyContent: replyContent};
	return client.put(`/reply/${replyId}`, data);
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

export function updatePreference(username, skills, desiredJobs, desiredLocations){
	const data = {userName: username, desiredJobsTitle: desiredJobs, desiredJobsLocation: desiredLocations, skills: skills};
	return client.put(`/profile/preference/${username}`, data);
};


//company tracking
export function getCompanies(username){
		
	return client.get(`/company/specific/${username}`,  {
		headers: {
			'Accept': 'application/json',
			'Content-Type': 'application/json',
		},
	})
	.then(response => {
		return response.data;
	})
	.catch(error => {
		throw error.response.data;
	});
};

export function deleteCompany(userName, companyId){
    const config = {
        data: { userName: userName }, 
    };	
	return client.delete(`/company/${userName}/${companyId}`, config);
};

export function addCompany(userName, companyName){
	const data = { userName: userName , companyName: companyName};
	return client.post(`/company/add`, data);
}

export function getAllCompany(username){
		
	return client.get(`/company/all/${username}`,  {
		headers: {
			'Accept': 'application/json',
			'Content-Type': 'application/json',
		},
	})
	.then(response => {
		return response.data;
	})
	.catch(error => {
		throw error.response.data;
	});
};

export function changeTracking(username, companyId){
	return client.put(`/company/${username}/${companyId}`);
}

//homepage
export function getSearchResult(username, jobTitle, company, level){
	return; 
}

//notification
export function getNotifications(username){
	return;
}

export function DeleteNotification(username , notificationId){
	return;
}

//progress Tracking



