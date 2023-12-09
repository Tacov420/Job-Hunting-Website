package JobHunting.serviceTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.*;

import JobHunting.model.*;
import JobHunting.repository.*;
import JobHunting.service.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {
    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private ReplyRepository replyRepository;

    @InjectMocks
    private PostService postService;

    Post setPost(int userId, int id, int categoryId, String postTitle, String postContent, LocalDateTime time) {
        Post post = new Post();
        post.setCategoryId(categoryId);
        post.setContent(postContent);
        post.setId(id);
        post.setTimestamp(time);
        post.setTitle(postTitle);
        post.setUserId(userId);
        return post;
    }

    Reply setReply(int userId, int id, int postId, String replyContent, LocalDateTime time) {
        Reply reply = new Reply();
        reply.setContent(replyContent);
        reply.setId(id);
        reply.setPostId(postId);
        reply.setTimestamp(time);
        reply.setUserId(userId);
        return reply;
    }

    @Test
    public void testGetPostList() {
        Post post1 = setPost(0, 0, 0, "TestTitle", "TestContent", LocalDateTime.parse("2023-11-28T08:09:12.651"));
        Post post2 = setPost(1, 1, 0, "TestTitle2", "TestContent2", LocalDateTime.parse("2023-12-07T01:33:33.867"));
        List<Post> postList = Arrays.asList(
            post1, post2            
        );
        when(postRepository.findByCategoryId(0)).thenReturn(postList);
        Object result = postService.getPostList(0, 0);
        Map<String, List<Object>> expected = new HashMap<>();
        expected.put("0", Arrays.asList("TestTitle", "2023-11-28 08:09:12", true));
        expected.put("1", Arrays.asList("TestTitle2", "2023-12-07 01:33:33", false));
        String expectedString = expected.toString();
        String resultString = result.toString();
        assertEquals(expectedString, resultString);
    }

    @Test
    public void testGetSpecificPost() {
        Post post1 = setPost(0, 0, 0, "TestTitle", "TestContent", LocalDateTime.parse("2023-11-28T08:09:12.651"));
        when(postRepository.findById(0)).thenReturn(post1);
        Reply reply1 = setReply(0, 0, 0, "TestContent", LocalDateTime.parse("2023-11-28T08:09:12.651"));
        Reply reply2 = setReply(1, 1, 0, "TestContent2", LocalDateTime.parse("2023-12-07T01:33:33.867"));
        List<Reply> replyList = Arrays.asList(
            reply1, reply2            
        );
        when(replyRepository.findByPostId(0)).thenReturn(replyList);
        Object result = postService.getSpecificPost(0, 0);
        Map<String, Object> expected = new HashMap<>();
        Map<String, List<Object>> replies = new HashMap<>();
        replies.put("0", Arrays.asList("TestContent", "2023-11-28 08:09:12", true));
        replies.put("1", Arrays.asList("TestContent2", "2023-12-07 01:33:33", false));
        expected.put("replies", Arrays.asList(replies));
        expected.put("postContent", "TestContent");
        expected.put("postTitle", "TestTitle");
        String expectedString = expected.toString();
        String resultString = result.toString();
        assertEquals(expectedString, resultString);
    }

    @Test
    public void testCreatePost() {
        when(postRepository.findFirstByOrderByIdDesc()).thenReturn(null);
        int userId = 0;
        int categoryId = 1;
        String postTitle = "777";
        String postContent = "88888";
        String result = postService.createPost(userId, categoryId, postTitle, postContent);
        assertEquals("Add post successfully", result);
    }

    @Test
    public void testEditPost() {
        Post post1 = setPost(0, 0, 0, "TestTitle", "TestContent", LocalDateTime.parse("2023-11-28T08:09:12.651"));
        when(postRepository.findById(0)).thenReturn(post1);
        int postId = 0;
        String postTitle = "777";
        String postContent = "88888";
        String result = postService.editPost(postId, postTitle, postContent);
        assertEquals("Edit post successfully", result);
    }

    @Test
    public void testDeletePost() {
        Reply reply1 = setReply(0, 0, 0, "TestContent", LocalDateTime.parse("2023-11-28T08:09:12.651"));
        Reply reply2 = setReply(1, 1, 0, "TestContent2", LocalDateTime.parse("2023-12-07T01:33:33.867"));
        List<Reply> replyList = Arrays.asList(
            reply1, reply2            
        );
        when(replyRepository.findByPostId(0)).thenReturn(replyList);
        String result = postService.deletePost(0);
        assertEquals("Delete post successfully", result);
    }
}
