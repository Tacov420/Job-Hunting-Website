package JobHunting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

import JobHunting.service.*;

@RestController
@RequestMapping("/api/reply")

public class ReplyController {
    @Autowired
    private ReplyService replyService;

    @PostMapping(value = "/add")
    public ResponseEntity<Object> addPost(@RequestBody Map<String, String> body) {
        String userName = body.get("userName");
        int postId = Integer.parseInt(body.get("postId"));
        String replyContent = body.get("replyContent");
        if (!replyService.checkPostId(postId)) {
            return new ResponseEntity<>("PostId is incorrect", HttpStatus.OK);
        }
        try {
            int userId = replyService.getUserId(userName);
            if (userId == -1) {
                return new ResponseEntity<>("UserName doesn't exist", HttpStatus.BAD_REQUEST);
            }
            String res = replyService.createReply(userId, postId, replyContent);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{replyId}")
    public ResponseEntity<Object> editReply(@PathVariable int replyId, @RequestBody Map<String, String> body) {
        String userName = body.get("userName");
        String replyContent = body.get("replyContent");
        if (!replyService.checkReplyId(replyId)) {
            return new ResponseEntity<>("ReplyId is incorrect", HttpStatus.BAD_REQUEST);
        }
        try {
            int userId = replyService.getUserId(userName);
            if (userId == -1) {
                return new ResponseEntity<>("UserName doesn't exist", HttpStatus.BAD_REQUEST);
            }
            if (!replyService.checkPermission(userId, replyId)) {
                return new ResponseEntity<>("User doesn't have permission", HttpStatus.FORBIDDEN);
            }
            String res = replyService.editReply(replyId, replyContent);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{replyId}")
    public ResponseEntity<Object> deletePost(@PathVariable int replyId, @RequestBody Map<String, String> body) {
        String userName = body.get("userName");
        if (!replyService.checkReplyId(replyId)) {
            return new ResponseEntity<>("ReplyId is incorrect", HttpStatus.BAD_REQUEST);
        }
        try {
            int userId = replyService.getUserId(userName);
            if (userId == -1) {
                return new ResponseEntity<>("UserName doesn't exist", HttpStatus.BAD_REQUEST);
            }
            if (!replyService.checkPermission(userId, replyId)) {
                return new ResponseEntity<>("User doesn't have permission", HttpStatus.FORBIDDEN);
            }
            String res = replyService.deleteReply(replyId);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
