import { env } from "./env";
import axios from 'axios';


const client = axios.create({
    baseURL: env.VITE_API_URL,
});

//login
export function getUser(username, password){
	const data = {userName: username, password: password};
	return axios.post("/login", data);
	return client.post("/login", data);
};


//register
export function createUser(username, password){
	const data = {userName: username, password: password};
	return axios.post("/register/personalInfo", data);
	return client.post("/register/personalInfo", data);
};

export function Verify(username, verificationCode){
	const data = {userName: username, verificationCode: verificationCode};
	return axios.post("/register/verify", data);
	return client.post("/register/verify", data);
};

export function createVerify(username, email){
	const data = {userName: username, email: email};
	return axios.post("/register/sendVerification", data);
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
	return axios.post("/register/preference", data);
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
	return axios.get(`/post/list/${username}/${categoryIdInt}`,  {
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
	return axios.get(`/post/specific/${username}/${postIdInt}`,  {
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
	return axios.post("/post/add", data);
};
export function editPost(username, postId, postTitle, postContent){
	const postIdInt = parseInt(postId, 10);
	const data = {userName: username , postTitle: postTitle, postContent: postContent};
	return axios.put(`/post/${postIdInt}`, data);
};
export function deletePost(userName, postId){
	const postIdInt = parseInt(postId, 10);
    const config = {
        data: { userName: userName }, 
    };	return axios.delete(`/post/${postIdInt}`, config);
};


export function addReply(username, postId, replyContent){
	const data = {userName: username , postId: postId, replyContent: replyContent};
	return axios.post("/reply/add", data);
};
export function deleteReply(userName, replyId){
    const config = {
        data: { userName: userName }, 
    };	return axios.delete(`/reply/${replyId}`, config);
};
export function editReply(username, replyId, replyContent){
	const data = {userName: username, replyContent: replyContent};
	return axios.put(`/reply/${replyId}`, data);
};


// Profile
export function getPersonalInfo(username){
	return axios.get(`/profile/${username}`);
};

export function updatePassword(username, newPassword){
	const data = {newPassword: newPassword, confirmPassword: newPassword};
	return axios.put(`/profile/${username}`, data);
};

export function getPreference(username){
	return axios.get(`/profile/preference/${username}`);
};

export function updatePreference(username, skills, desiredJobs, desiredLocations){
	const data = {userName: username, desiredJobsTitle: desiredJobs, desiredJobsLocation: desiredLocations, skills: skills};
	return axios.put(`/profile/preference/${username}`, data);
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
export function getSearchResult(jobTitle, company, level){
	const levelInt = parseInt(level, 10);
	return client.get(`/search/company=${company}&jobTitle=${jobTitle}&level=${levelInt}`, {
		headers: {
			'Accept': 'application/json',
			'Content-Type': 'application/json',
		},
	})
}

//notification
export function getNotifications(username){
	return client.get(`/notification/${username}`, {
		headers: {
			'Accept': 'application/json',
			'Content-Type': 'application/json',
		},
	})
}

export function DeleteNotification(username , notificationId){
	return client.delete(`/notification/${username}/${notificationId}`);
	
}

//progress Tracking
export function getColor(status){
	switch (status){
		case 0: return "bg-amber-200 text-amber-800";
		case 1: return "bg-green-400 text-green-800";
		case 2: return "bg-red-300 text-red-800";
		case 3: return "bg-slate-500 text-slate-200";
		default: return "bg-green-400 text-green-800";
	}
}

export function getColorHover(status){
	switch (status){
		case 0: return "bg-amber-200 text-amber-800 hover:bg-amber-300";
		case 1: return "bg-green-400 text-green-800 hover:bg-green-500";
		case 2: return "bg-red-300 text-red-800 hover:bg-red-400";
		case 3: return "bg-slate-500 text-slate-200 hover:bg-slate-400";
		default: return "bg-green-400 text-green-800 hover:bg-green-500";
	}
}

export function getProgresses(username){
	return client.get(`/progress/${username}`,  {
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
}

export function addProgress(username , companyName , jobTitle , stage, date , status){
	const data = { 
		companyName : companyName,   
		jobTitle: jobTitle, 
		stage: stage,
		date: date,
		status: status,
	};
	return client.post(`/progress/${username}/add`, data);
}

export function getProgress(username , progressId ){
	const progressIdInt = parseInt(progressId, 10);
	return client.get(`/progress/${username}/${progressIdInt}`,  {
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
}

export function addStage(username , progressId, stageName , date , status){
	const data = { 
		stageName: stageName,
		date: date,
		status: status,
	};
	return client.put(`/progress/${username}/${progressId}/add`, data);
}

export function updateStage(username , progressId, index , stageName , date , status){
	const data = { 
		index: index,
		stageName: stageName,
		date: date,
		status: status,
	};
	return client.put(`/progress/${username}/${progressId}/edit`, data);
}

export function deleteProgress(username, progressId){
	return client.delete(`/progress/${username}/${progressId}`);
};








