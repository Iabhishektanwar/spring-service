package com.JPA.JPA.Controller;

import com.JPA.JPA.Dao.UserRepository;
import com.JPA.JPA.Entity.User;
import com.JPA.JPA.Helper.FileUploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.util.*;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileUploader fileUploader;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> allUsers = new ArrayList<>();
        Iterable<User> users = userRepository.findAll();
        users.forEach(user -> {
            allUsers.add(user);
        });
        if(allUsers.size() == 0) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.of(Optional.of(allUsers));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Optional<User>> getUser(@PathVariable("id") String id) {
        Optional<User> user = null;
        try {
            user = userRepository.findById(UUID.fromString(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(user.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.of(Optional.of(user));
    }

    @PostMapping("/users")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        try {
            userRepository.save(user);
            return ResponseEntity.of(Optional.of(user));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") String id) {
        try {
            userRepository.deleteById(UUID.fromString(id));
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable("id") String id) {
        Optional<User> userOptional = userRepository.findById(UUID.fromString(id));

        if (userOptional.isPresent()) {
            User updatedUser = userOptional.get();
            updatedUser.setName(user.getName());
            updatedUser.setAge(user.getAge());
            updatedUser.setAddress(user.getAddress());
            return ResponseEntity.of(Optional.of(userRepository.save(updatedUser)));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/uploads")
    public ResponseEntity<String> importFile(@RequestParam("file") MultipartFile file) {
        try {
            boolean uploaded = fileUploader.upload(file);
            if(uploaded == true) {
                return ResponseEntity.ok(ServletUriComponentsBuilder.fromCurrentContextPath().path("uploads")
                        .path(File.separator)
                        .path(file.getOriginalFilename()).toUriString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed! Please try again");
    }

}

