package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;

import java.security.Principal;

@Controller
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/user")
    public String getUser(Principal principal, Model model) {
        model.addAttribute("userLogin", userService.getUserByEmail(principal.getName()));
        return "User";
    }
}