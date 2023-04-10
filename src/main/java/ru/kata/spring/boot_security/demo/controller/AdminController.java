package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final RoleService roleService;
    private final UserService userService;
    @Autowired
    public AdminController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }
    @GetMapping()
    public String userList(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("users", userService.findAll());
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User u = (User) userService.findByUsername(name);
        model.addAttribute("user", u);

        return "usersPage";
    }


    @GetMapping("/{idShow}")
    public String show(@PathVariable("idShow") Long id, Model model) {
        model.addAttribute("user", (User) userService.getById(id));
        return "usersPage";
    }

    @GetMapping("/add")
    public String newUser(@ModelAttribute("user") User user) {
        return "addUser";
    }

    // было без new
    @PostMapping("/add")
    public String createUser(@ModelAttribute("user") @Valid User user,
                             BindingResult bindingResult) { // проверка на валидность введ данных
        if (bindingResult.hasErrors()) {
            return "addUser";
        }
        userService.save(user); // addUser(user);
        return "redirect:/admin";

    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }

    @GetMapping("/delete/{idDelete}")
    public String showDelete(@PathVariable("idDelete") Long id, Model model) {
        model.addAttribute("user", userService.getById(id));
        return "showDelete";
    }

    //     методы для обновления данных юзера  /users/3/edit  без bootstrap
    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") Long id) {
        User user = userService.getById(id);
        List<Role> listRoles = roleService.findAllRole();
        model.addAttribute("user", user);
        model.addAttribute("listRoles", listRoles);
        return "editPage";
    }
    @PatchMapping("/save/{id}")
    public String saveUser(@PathVariable("id") Long id,User user) {
        userService.update(id,user);

        return "redirect:/admin";
    }
}
