package com.example.quizbackend.service;

import com.example.quizbackend.modal.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface UserService {
    User saveUser(User user);
    List<User> displayUsers();
    User findUserById(Long id);

    User findUserByUsername(String username);
    User findUserByEmail(String email);

    void deleteUserById(Long id);

}
