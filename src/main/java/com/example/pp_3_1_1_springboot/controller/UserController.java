package com.example.pp_3_1_1_springboot.controller;

import com.example.pp_3_1_1_springboot.models.User;
import com.example.pp_3_1_1_springboot.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String index (Model model) {
        model.addAttribute("users", userService.index());
        return "users/index";
    }

    @GetMapping("/{id}")
    public String show (@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.showUser(id));
        return "users/show";
    }

    @GetMapping("/new")
    public String newUser (Model model) {
        model.addAttribute("user", new User());
        return "users/new";
    }

    @PostMapping()
    public String create (@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}/edit")
    public String edit (@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.showUser(id));
        return "users/edit";
    }

    @PutMapping("/{id}")
    public String update (@ModelAttribute("user") User user,
                          @PathVariable("id") int id) {
        userService.showUser(id);
        userService.update(user);
        return "redirect:/users";
    }

    @DeleteMapping("/{id}")
    public String deleteUser (@PathVariable("id") int id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }
}
