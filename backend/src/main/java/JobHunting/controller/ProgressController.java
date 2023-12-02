// package JobHunting.controller;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.ExceptionHandler;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;

// import JobHunting.model.Progress;
// import JobHunting.service.ProgressService;
// import JobHunting.service.ProgressService.*;

// import javax.validation.Valid;

// @RestController
// @RequestMapping("/api/progress")
// @CrossOrigin(origins = "http://localhost:3000") // 允许来自前端的请求

// public class ProgressController {

// @Autowired
// private ProgressService progressService;

// // CREATE
// @PostMapping
// public ResponseEntity<Progress> createProgress(@Valid @RequestBody Progress
// progress) {
// Progress createdProgress = progressService.createProgress(progress);
// return new ResponseEntity<>(createdProgress, HttpStatus.CREATED);
// }

// // READ
// @GetMapping("/progress/{userName}")
// public ResponseEntity<Progress> getProgressByUserName(@PathVariable String
// userName,
// @RequestParam UpdateType updateType) {
// try {
// Progress progress = progressService.getProgressByUserName(userName,
// updateType);
// return new ResponseEntity<>(progress, HttpStatus.OK);
// } catch (ProgressNotFoundException e) {
// return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
// } catch (Exception e) {
// return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
// }
// }

// // UPDATE
// @PutMapping("/progress/{userName}")
// public ResponseEntity<Progress> updateProgress(@PathVariable String userName,
// @RequestBody Progress progress) {
// try {
// Progress updatedProgress = progressService.updateProgress(userName,
// progress);
// return new ResponseEntity<>(updatedProgress, HttpStatus.OK);
// } catch (ProgressNotFoundException e) {
// return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
// } catch (Exception e) {
// return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
// }
// }

// // DELETE
// @DeleteMapping("/progress/{userName}")
// public ResponseEntity<Void> deleteProgress(@PathVariable String userName,
// @RequestBody Progress progress) {
// progressService.deleteProgress(userName, progress);
// return ResponseEntity.noContent().build();
// }

// @ExceptionHandler(value = Exception.class)
// public ResponseEntity<Object> exception(Exception exception) {
// return new ResponseEntity<>("Progress Not Found", HttpStatus.NOT_FOUND);
// }

// }
