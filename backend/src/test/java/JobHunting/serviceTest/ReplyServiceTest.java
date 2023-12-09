package JobHunting.serviceTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import JobHunting.model.*;
import JobHunting.repository.*;
import JobHunting.service.*;

@ExtendWith(MockitoExtension.class)
public class ReplyServiceTest {
    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private ReplyRepository replyRepository;

    @InjectMocks
    private ReplyService replyService;

    public Reply setReply(int id, int userId, int postId, String content, LocalDateTime time) {
        Reply reply = new Reply();
        reply.setContent(content);
        reply.setId(id);
        reply.setPostId(postId);
        reply.setTimestamp(time);
        reply.setUserId(userId);
        return reply;
    }

    @Test
    public void testCreateReply() {
        when(replyRepository.findFirstByOrderByIdDesc()).thenReturn(null);
        int userId = 0;
        int postId = 1;
        String replyContent = "TestReplyContent";
        String result = replyService.createReply(userId, postId, replyContent);
        assertEquals("Add reply successfully", result);
    }

    @Test
    public void testEditReply() {
        Reply reply1 = setReply(0, 0, 0, "TestReplyContent", LocalDateTime.parse("2023-11-28T08:09:12.651"));
        when(replyRepository.findById(0)).thenReturn(reply1);
        String replyContent = "EditReplyContent";
        String result = replyService.editReply(0, replyContent);
        assertEquals("Edit reply successfully", result);
    }

    @Test
    public void testDeleteReply() {
        String result = replyService.deleteReply(0);
        assertEquals("Delete reply successfully", result);
    }
}
