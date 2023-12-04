package JobHunting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

import JobHunting.service.*;

@RestController
@RequestMapping("/api/company")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @GetMapping(value = "/all/{userName}")
    public ResponseEntity<Object> getCompany(@PathVariable String userName) {
        try {
            int userId = companyService.getUserId(userName);
            if (userId == -1) {
                return new ResponseEntity<>("UserName doesn't exist", HttpStatus.BAD_REQUEST);
            }
            Object res = companyService.getAllCompany(userId);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/add")
    public ResponseEntity<Object> addCompany(@RequestBody Map<String, String> body) {
        String userName = body.get("userName");
        String companyName = body.get("companyName");
        try {
            int userId = companyService.getUserId(userName);
            if (userId == -1) {
                return new ResponseEntity<>("UserName doesn't exist", HttpStatus.BAD_REQUEST);
            }
            String res = companyService.addCompany(userId, companyName);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/specific/{userName}")
    public ResponseEntity<Object> getSpecificCompany(@PathVariable String userName) {
        try {
            int userId = companyService.getUserId(userName);
            if (userId == -1) {
                return new ResponseEntity<>("UserName doesn't exist", HttpStatus.BAD_REQUEST);
            }
            Object res = companyService.getSpecificCompany(userId);
            return new ResponseEntity<>(res, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{userName}/{companyId}")
    public ResponseEntity<Object> updateCompany(@PathVariable String userName, @PathVariable int companyId) {
        try {
            int userId = companyService.getUserId(userName);
            if (userId == -1) {
                return new ResponseEntity<>("UserName doesn't exist", HttpStatus.BAD_REQUEST);
            }
            if (!companyService.checkCompanyId(companyId)) {
                return new ResponseEntity<>("CompanyId doesn't exist", HttpStatus.BAD_REQUEST);
            }
            if (!companyService.checkPermission(userId, companyId)) {
                return new ResponseEntity<>("User doesn't have permission", HttpStatus.FORBIDDEN);
            }
            Object res = companyService.updateCompany(companyId);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{userName}/{companyId}")
    public ResponseEntity<Object> deleteCompany(@PathVariable String userName, @PathVariable int companyId) {
        try {
            int userId = companyService.getUserId(userName);
            if (userId == -1) {
                return new ResponseEntity<>("UserName doesn't exist", HttpStatus.BAD_REQUEST);
            }
            if (!companyService.checkCompanyId(companyId)) {
                return new ResponseEntity<>("CompanyId doesn't exist", HttpStatus.BAD_REQUEST);
            }
            if (!companyService.checkPermission(userId, companyId)) {
                return new ResponseEntity<>("User doesn't have permission", HttpStatus.FORBIDDEN);
            }
            Object res = companyService.deleteCompany(companyId);
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
