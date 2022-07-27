package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;
import java.util.List;

@RestController
public class UserRestController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin/allUsers")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/admin/getUser/{id}")
    public ResponseEntity<User> getUserBy(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(userService.getBy(id), HttpStatus.OK);
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Integer id) {
        userService.deleteBy(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/user/getRolesAmount")
    public ResponseEntity<Integer> getRolesAmount(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return new ResponseEntity<>(user.getAuthorities().size(), HttpStatus.OK);
    }

    @PostMapping("/admin/newUser")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        userService.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/admin/getAllRoles")
    public ResponseEntity<List<Role>> getAllRoles() {
        return new ResponseEntity<>(roleService.getAllRoles(), HttpStatus.OK);
    }

    @PutMapping("/admin/edit")
    public ResponseEntity<User> editUserBy(@RequestBody User user) {
        userService.edit(user.getId(), user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
