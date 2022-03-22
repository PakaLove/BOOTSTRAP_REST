package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.util.HashSet;
import java.util.List;


@Controller
@RequestMapping()
public class UserController {
    @Autowired
    UserServiceImpl userServiceImpl;
    @Autowired
    RoleServiceImpl roleService;
    @GetMapping("/")
    public String main(Model model) {
        return "main";
    }
    @GetMapping("/admin/users")
    public String showAll(Model model) {
     /*   String name = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("user", userServiceImpl.loadUserByUsername(name));
        model.addAttribute("users", userServiceImpl.getAllUsers());
        model.addAttribute("roleList", roleService.getAllRoles());*/
        return "allusers";
    }

    @GetMapping("/user")
    public String showUserById(Model model) {
      //  String name = SecurityContextHolder.getContext().getAuthentication().getName();
       // model.addAttribute("user", userServiceImpl.loadUserByUsername(name));
        return "userpage";
    }
    @GetMapping("/default")
    public String redirectToUserID() {
        return "redirect:/user";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

}
