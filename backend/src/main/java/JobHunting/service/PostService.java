package JobHunting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Locale;

import JobHunting.repository.*;
import JobHunting.model.*;

@Service
public class PostService {
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ReplyRepository replyRepository;

    public int getUserId(String userName) {
        Profile profile = profileRepository.findByUserName(userName);
        if (profile == null) {
            return -1;
        }
        return profile.getId();
    }

    public Object getPostList(int userId, int categoryId) {
        List<Post> posts = postRepository.findByCategoryId(categoryId);
        Map<String, List<Object>> returnMap = new HashMap<>();
        for (Post post: posts) {
            List<Object> postDetails = new ArrayList<>();
            postDetails.add(post.getTitle());
            LocalDateTime dateTime = LocalDateTime.parse(post.getTimestamp().toString(), DateTimeFormatter.ISO_DATE_TIME);
            DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.US);
            String formatted = dateTime.format(customFormatter);
            postDetails.add(formatted.toString());
            if (post.getUserId() == userId) {
                postDetails.add(true);
            } else {
                postDetails.add(false);
            }
            returnMap.put(String.valueOf(post.getId()), postDetails);
        }
        
        return returnMap;
    }

    public Object getSpecificPost(int userId, int postId) {
        Post post = postRepository.findById(postId);
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("postTitle", String.valueOf(post.getTitle()));
        returnMap.put("postContent", String.valueOf(post.getContent()));

        List<Reply> replies = replyRepository.findByPostId(postId);
        Map<String, List<Object>> replyMap = new HashMap<>();
        for (Reply reply: replies) {
            List<Object> replyDetails = new ArrayList<>();
            replyDetails.add(reply.getContent());
            LocalDateTime dateTime = LocalDateTime.parse(reply.getTimestamp().toString(), DateTimeFormatter.ISO_DATE_TIME);
            DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.US);
            String formatted = dateTime.format(customFormatter);
            replyDetails.add(formatted.toString());
            if (reply.getUserId() == userId) {
                replyDetails.add(true);
            } else {
                replyDetails.add(false);
            }
            replyMap.put(String.valueOf(reply.getId()), replyDetails);
        }
        List<Object> repliesDetails = new ArrayList<>();
        repliesDetails.add(replyMap);
        returnMap.put("replies", repliesDetails);
        
        return returnMap;
    }

    public boolean checkPostId(int postId) {
        Post post = postRepository.findById(postId);
        if (post == null) {
            return false;
        }
        return true;
    }

    public String createPost(int userId, int categoryId, String postTitle, String postContent) {
        int id;
        Post largestPost = postRepository.findFirstByOrderByIdDesc();
        if (largestPost != null){
            id = largestPost.getId() + 1;
        } else {
            id = 0;
        }
        Post post = new Post();
        LocalDateTime time = LocalDateTime.now();
        post.setPost(userId, categoryId, postTitle, postContent, id, time);
        postRepository.save(post);

        return "Add post successfully";
    }

    public boolean checkPermission(int userId, int postId) {
        Post post = postRepository.findById(postId);
        if (post.getUserId() != userId) {
            return false;
        }
        return true;
    }

    public String editPost(int postId, String postTitle, String postContent) {
        Post post = postRepository.findById(postId);
        LocalDateTime time = LocalDateTime.now();
        post.setTitle(postTitle);
        post.setContent(postContent);
        post.setTimestamp(time);
        postRepository.save(post);

        return "Edit post successfully";
    }

    public String deletePost(int postId) {
        List<Reply> replies = replyRepository.findByPostId(postId);
        for (Reply reply: replies) {
            replyRepository.deleteById(reply.getId());
        }
        postRepository.deleteById(postId);

        return "Delete post successfully";
    }
}
