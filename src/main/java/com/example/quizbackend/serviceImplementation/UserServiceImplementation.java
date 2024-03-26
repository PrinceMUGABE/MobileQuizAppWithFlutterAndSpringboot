package com.example.quizbackend.serviceImplementation;

import com.example.quizbackend.modal.User;
import com.example.quizbackend.repository.UserRepository;
import com.example.quizbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> displayUsers() {
        return userRepository.findAll();
    }


    @Override
    public User findUserById(Long id) {
        Optional<User> savedUser=userRepository.findById(id);
        return savedUser.orElse(null);
    }
    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }


}
