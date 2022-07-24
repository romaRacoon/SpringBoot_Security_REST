package com.kata.pproma.controllers;

import com.kata.pproma.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/allUsers")
    public String getAllUsers(Model model) {
        model.addAttribute("allUsers", userService.getAllUsers());
        return "allUsers";
    }
}
