package com.example.democheck.service;

import com.example.democheck.model.User;
import com.example.democheck.util.ExcelHelper;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    public void addUser(User user) {
        ExcelHelper.writeUser(user);
    }

    public List<User> getAllUsers() {
        return ExcelHelper.readUsers();
    }

    public void updateUser(String email, User user) {
        ExcelHelper.updateUser(email, user);
    }

    public void deleteUser(String email) {
        ExcelHelper.deleteUser(email);
    }
}
