import { env } from "./env";
import axios from 'axios';


const client = axios.create({
    baseURL: env.VITE_API_URL,
});
const experience_posts = [{'id': 0, 'userId': 0, 'title': 'Interview experience of SLEEPING', 'content': 'The interviewer was very nice and asked me to lie on a comfortable bed for two hours. After I woke up, HR told me that I had been accepted! What an interesting Interview experience!'}, {'id': 2, 'userId': 1, 'title': 'Interview experience of SLEEPING', 'content': 'I feel like everyone in the company loves sleeping as much as I do. \tAs long as you show that you are sleepy during the interview, you will be accepted.' }]
const question_posts = [{'id': 1, 'userId': 1, 'title': 'Interview questions of SLEEPING', 'content': 'The interviewer only asked me what was the longest time I had slept for. I answered twenty hours.'}]
const company_posts = [{}]
const referral_posts = [{'id': 3, 'userId': 1, 'title': 'Referral of SLEEPING', 'content': 'Does anyone want to help me with internal referrals? plz... orz... '}]
const post_list = {0: experience_posts, 1: question_posts, 2: company_posts, 3:referral_posts}

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
	//console.log(data);
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


