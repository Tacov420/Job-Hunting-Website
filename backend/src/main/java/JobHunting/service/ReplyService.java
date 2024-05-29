package JobHunting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import JobHunting.repository.*;
import JobHunting.model.*;

@Service
public class ReplyService {
    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private PostRepository postRepository;

    public int getUserId(String userName) {
        Profile profile = profileRepository.findByUserName(userName);
        if (profile == null) {
            return -1;
        }
        return profile.getId();
    }

    public boolean checkPostId(int postId) {
        Post post = postRepository.findById(postId);
        if (post == null) {
            return false;
        }
        return true;
    }

    public boolean checkReplyId(int replyId) {
        Reply reply = replyRepository.findById(replyId);
        if (reply == null) {
            return false;
        }
        return true;
    }

    public String createReply(int userId, int postId, String replyContent) {
        int id;
        Reply largestReply = replyRepository.findFirstByOrderByIdDesc();
        if (largestReply != null){
            id = largestReply.getId() + 1;
        } else {
            id = 0;
        }
        Reply reply = new Reply();
        LocalDateTime time = LocalDateTime.now();
        reply.setReply(userId, postId, replyContent, id, time);
        replyRepository.save(reply);

        return "Add reply successfully";
    }

    public boolean checkPermission(int userId, int replyId) {
        Reply reply = replyRepository.findById(replyId);
        if (reply.getUserId() != userId) {
            return false;
        }
        return true;
    }

    public String editReply(int replyId, String replyContent) {
        Reply reply = replyRepository.findById(replyId);
        LocalDateTime time = LocalDateTime.now();
        reply.setContent(replyContent);
        reply.setTimestamp(time);
        replyRepository.save(reply);

        return "Edit reply successfully";
    }

    public String deleteReply(int replyId) {
        replyRepository.deleteById(replyId);

        return "Delete reply successfully";
    }
}
