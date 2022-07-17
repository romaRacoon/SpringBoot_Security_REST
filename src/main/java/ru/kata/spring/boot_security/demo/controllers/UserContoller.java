package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasRole('USER')")
public class UserContoller {
    private final UserService userService;

    @Autowired
    public UserContoller(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String getInfoPage(Model model, Principal principal) {
        UserDetails user = userService.loadUserByUsername(principal.getName());
        model.addAttribute("principalUser", user);
        return "user";
    }
}
