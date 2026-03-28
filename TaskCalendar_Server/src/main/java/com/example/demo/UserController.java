package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private BackService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user){
        User authenticatedUser = userService.saveUser(user);
        if (authenticatedUser != null) {
            return ResponseEntity.ok(authenticatedUser.getUserTasks());
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user){
        User authenticatedUser = userService.authenticate(user);
        if (authenticatedUser != null) {
            return ResponseEntity.ok(authenticatedUser.getUserTasks());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("");
        }
    }

    @PutMapping("/updateTasks/{userName}")
    public ResponseEntity<?> updateTasks(@PathVariable String userName, @RequestBody ArrayList<TaskSender> newTasks) {
        try {
            userService.updateTasks(userName, newTasks);
            return ResponseEntity.ok("Tasks updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
