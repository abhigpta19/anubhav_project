package com.example.democheck.controller;

import com.example.democheck.model.User;
import com.example.democheck.service.UserService;
import com.example.democheck.util.ExcelHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    @Autowired
    private UserService service;

    static {
        ExcelHelper.initExcelIfNeeded();
    }

    @PostMapping
    public String createUser(@RequestBody User user) {
        System.out.println("this is working");
        service.addUser(user);
        return "User added successfully.";
    }

    @GetMapping
    public List<User> getAllUsers() {
        return service.getAllUsers();
    }

    @PutMapping("/{email}")
    public String updateUser(@PathVariable String email, @RequestBody User user) {
        service.updateUser(email, user);
        return "User updated successfully.";
    }

    @DeleteMapping("/{email}")
    public String deleteUser(@PathVariable String email) {
        service.deleteUser(email);
        return "User deleted successfully.";
    }
}
