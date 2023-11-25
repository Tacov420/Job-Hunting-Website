package JobHunting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import JobHunting.service.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000") // 允许来自前端的请求
@RestController
@RequestMapping("/api/login")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping(value = "", consumes = "application/json")
    public ResponseEntity<String> checkLogin(@RequestBody Map<String, String> body) {
        String userName = body.get("userName");
        String password = body.get("password");
        try {
            String res = loginService.login(userName, password);
            if (res == "Hasn't filled in preference") {
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            } else if (res == "Hasn't verified") {
                return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);
            } else if (res == "Password is incorrect") {
                return new ResponseEntity<>(res, HttpStatus.FORBIDDEN);
            } else if (res == "Username hasn't been registered") {
                return new ResponseEntity<>(res, HttpStatus.FORBIDDEN);
            }
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
