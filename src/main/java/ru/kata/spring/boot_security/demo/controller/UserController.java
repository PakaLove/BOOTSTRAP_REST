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
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("user", userServiceImpl.loadUserByUsername(name));
        model.addAttribute("users", userServiceImpl.getAllUsers());
        model.addAttribute("roleList", roleService.getAllRoles());
        return "allusers";
    }

    @GetMapping("/admin/user/{id}")
    public String showUsersById(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userServiceImpl.getUserById(id));
        return "userpage";
    }
    @GetMapping("/user")
    public String showUserById(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("user", userServiceImpl.loadUserByUsername(name));
        return "userpage";
    }
    @GetMapping("/default")
    public String redirectToUserID() {
        return "redirect:/user";
    }

    @GetMapping("/admin/users/adduser")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "adduser";
    }


    @PostMapping("/admin/users")
    public String create(@ModelAttribute("username") String username,
                         @ModelAttribute("password") String password,
                         @ModelAttribute("lastname") String lastname,
                         @ModelAttribute("email") String email,
                         @RequestParam("roles") List<String> roles) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setLastname(lastname);
        user.setEmail(email);
        System.out.println(roles);
        HashSet<Role> set = new HashSet<>();
        for(String role : roles){
            set.add(roleService.getRoleByName(role));
        }

        user.setRoles(set);
        userServiceImpl.addUser(user);
        return "redirect:/admin/users";

    }

    @GetMapping("/admin/users/{id}/edit")
    public String editUser(Model model, @PathVariable("id") long id) {
        model.addAttribute("user", userServiceImpl.getUserById(id));
        return "updateuser";
    }

    @PostMapping("/admin/users/{id}")
    public String updateUser(@ModelAttribute("id") Long id,
                             @ModelAttribute("username") String username,
                             @ModelAttribute("password") String password,
                             @ModelAttribute("lastname") String lastname,
                             @ModelAttribute("email") String email){
        User defaultUser = userServiceImpl.getUserById(id);
        defaultUser.setUsername(username);
        defaultUser.setPassword(password);
        defaultUser.setLastname(lastname);
        defaultUser.setEmail(email);
        userServiceImpl.updateUser(defaultUser);
        return "redirect:/admin/users";
    }

    @DeleteMapping("/admin/users/{id}")
    public String deleteUser(@ModelAttribute("id") Long id){
        userServiceImpl.removeUserById(id);
        System.out.println(id);
        return "redirect:/admin/users";
    }
    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

}
