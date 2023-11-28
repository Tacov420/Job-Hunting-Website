package JobHunting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

import JobHunting.service.*;

@RestController
@RequestMapping("/api/post")
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping(value = "/list/{userName}/{categoryId}", consumes = "application/json")
    public ResponseEntity<Object> getPostList(@PathVariable String userName, @PathVariable int categoryId) {
        if (categoryId != 0 && categoryId != 1  && categoryId != 2 && categoryId != 3) {
            return new ResponseEntity<>("CategoryId is incorrect", HttpStatus.BAD_REQUEST);
        }
        try {
            int userId = postService.getUserId(userName);
            if (userId == -1) {
                return new ResponseEntity<>("UserName doesn't exist", HttpStatus.BAD_REQUEST);
            }
            Object res = postService.getPostList(userId, categoryId);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/specific/{userName}/{postId}", consumes = "application/json")
    public ResponseEntity<Object> getSpecificPost(@PathVariable String userName, @PathVariable int postId) {
        if (!postService.checkPostId(postId)) {
            return new ResponseEntity<>("PostId is incorrect", HttpStatus.BAD_REQUEST);
        }
        try {
            int userId = postService.getUserId(userName);
            if (userId == -1) {
                return new ResponseEntity<>("UserName doesn't exist", HttpStatus.BAD_REQUEST);
            }
            Object res = postService.getSpecificPost(userId, postId);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/add", consumes = "application/json")
    public ResponseEntity<Object> addPost(@RequestBody Map<String, String> body) {
        String userName = body.get("userName");
        int categoryId = Integer.parseInt(body.get("categoryId"));
        String postTitle = body.get("postTitle");
        String postContent = body.get("postContent");
        if (categoryId != 0 && categoryId != 1  && categoryId != 2 && categoryId != 3) {
            return new ResponseEntity<>("CategoryId is incorrect", HttpStatus.BAD_REQUEST);
        }
        try {
            int userId = postService.getUserId(userName);
            if (userId == -1) {
                return new ResponseEntity<>("UserName doesn't exist", HttpStatus.BAD_REQUEST);
            }
            String res = postService.createPost(userId, categoryId, postTitle, postContent);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{postId}", consumes = "application/json")
    public ResponseEntity<Object> editPost(@PathVariable int postId, @RequestBody Map<String, String> body) {
        String userName = body.get("userName");
        String postTitle = body.get("postTitle");
        String postContent = body.get("postContent");
        if (!postService.checkPostId(postId)) {
            return new ResponseEntity<>("PostId is incorrect", HttpStatus.BAD_REQUEST);
        }
        try {
            int userId = postService.getUserId(userName);
            if (userId == -1) {
                return new ResponseEntity<>("UserName doesn't exist", HttpStatus.BAD_REQUEST);
            }
            if (!postService.checkPermission(userId, postId)) {
                return new ResponseEntity<>("User doesn't have permission", HttpStatus.FORBIDDEN);
            }
            String res = postService.editPost(postId, postTitle, postContent);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{postId}", consumes = "application/json")
    public ResponseEntity<Object> deletePost(@PathVariable int postId, @RequestBody Map<String, String> body) {
        String userName = body.get("userName");
        if (!postService.checkPostId(postId)) {
            return new ResponseEntity<>("PostId is incorrect", HttpStatus.BAD_REQUEST);
        }
        try {
            int userId = postService.getUserId(userName);
            if (userId == -1) {
                return new ResponseEntity<>("UserName doesn't exist", HttpStatus.BAD_REQUEST);
            }
            if (!postService.checkPermission(userId, postId)) {
                return new ResponseEntity<>("User doesn't have permission", HttpStatus.FORBIDDEN);
            }
            String res = postService.deletePost(postId);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
