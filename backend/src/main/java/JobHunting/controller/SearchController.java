package JobHunting.controller;

import JobHunting.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/search")
public class SearchController {
  @Autowired private SearchService searchService;

  @GetMapping(value = "/company={company}&jobTitle={jobTitle}&level={level}")
  public ResponseEntity<Object> searchByKeyword(
      @PathVariable String company, @PathVariable String jobTitle, @PathVariable int level) {
    if (level != 0 && level != 1 && level != 2) {
      return new ResponseEntity<>("Level is incorrect", HttpStatus.BAD_REQUEST);
    }

    try {
      Object res = searchService.getJobs(company, jobTitle, level);
      return new ResponseEntity<>(res, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
