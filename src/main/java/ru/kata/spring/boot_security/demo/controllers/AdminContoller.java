package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminContoller {
    private final UserService userService;

    @Autowired
    public AdminContoller(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    @SuppressWarnings("unchecked")
    public String getMainAdminPage(Model model) {
        System.out.println(userService);
        model.addAttribute("users", userService.findAll());
        return "allUsers";
    }

    @GetMapping("/create")
    public String createUserForm(Model model) {
        model.addAttribute("newUser", new User());
        return "create";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute("newUser") User user, Model model) {
        userService.save(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
        userService.deleteBy(id);
        return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("edittedUser", userService.getBy(id));
        return "edit";
    }

    @PutMapping("/edit/{id}")
    public String editUser(@PathVariable("id") Integer id,
                           @ModelAttribute("edittedUser") User user) {
        userService.edit(id, user);
        return "redirect:/admin";
    }
}
