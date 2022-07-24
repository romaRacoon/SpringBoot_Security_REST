package com.kata.pproma.controllers;

import com.kata.pproma.models.User;
import com.kata.pproma.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class UserRestController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long id){
        User user = userService.getUserById(id);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/users")
    public void createUser(@RequestBody User user) {
        userService.addUser(user);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long id,
                                                   @RequestBody User userDetails) {
        User user = userService.getUserById(id);

        user.setEmail(userDetails.getEmail());
        user.setSecondName(userDetails.getSecondName());
        user.setFirstName(userDetails.getFirstName());
        final User updatedUser = userService.editUser(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/users/{id}")
    public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long id) {
        User user = userService.getUserById(id);

        userService.deleteUser(user);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
