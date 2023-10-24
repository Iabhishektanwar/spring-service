package com.JPA.JPA.Controller;

import com.JPA.JPA.Dao.UserRepository;
import com.JPA.JPA.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getUsers() {
        List<User> allUsers = new ArrayList<>();

        Iterable<User> users = userRepository.findAll();
        users.forEach(user -> {
            allUsers.add(user);
        });

        return allUsers;
    }

    @GetMapping("/users/{id}")
    public Optional<User> getUser(@PathVariable("id") String id) {
        return userRepository.findById(UUID.fromString(id));
    }

    @PostMapping("/users")
    public User addUser(@RequestBody User user) {
        userRepository.save(user);
        return user;
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable("id") String id) {
        userRepository.deleteById(UUID.fromString(id));
        return "User deleted successfully";
    }

    @PutMapping("/users/{id}")
    public User updateUser(@RequestBody User user, @PathVariable("id") String id) {
        Optional<User> userOptional = userRepository.findById(UUID.fromString(id));

        if (userOptional.isPresent()) {
            User updatedUser = userOptional.get();
            updatedUser.setName(user.getName());
            updatedUser.setAge(user.getAge());
            updatedUser.setAddress(user.getAddress());
            return userRepository.save(updatedUser);
        } else {
            return null;
        }
    }

}

