package com.example.quizbackend.controller;

import com.example.quizbackend.modal.User;
import com.example.quizbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService; // Use UserService interface

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody User user) {
        try {
            System.out.println("Connected to server");
            // Validate user inputs
            if (!isValidEmail(user.getEmail())) {
                return ResponseEntity.badRequest().body("Invalid email format. Please provide a valid email.");
            }
            // Check if username or email already exists
            if (userService.findUserByUsername(user.getUsername()) != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists. Please choose a different username.");
            }
            if (userService.findUserByEmail(user.getEmail()) != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists. Please use a different email.");
            }

            // Save user data into database
            userService.saveUser(user);
            System.out.println("User Saved successfully: " + user.getUsername());
            return ResponseEntity.ok("User created successfully. Please login.");
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace for debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create user.");
        }
    }

    // Method to validate email format
    private boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$";
        return email.matches(regex);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> listUsers() {
        List<User> users = userService.displayUsers();
        return ResponseEntity.ok(users);
    }



    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            // Validate user inputs
            if (user.getUsername() == null || user.getUsername().isEmpty()) {
                return ResponseEntity.badRequest().body("Please enter a username");
            }
            if (user.getPassword() == null || user.getPassword().isEmpty()) {
                return ResponseEntity.badRequest().body("Please enter a password");
            }

            // Check if the username and password are valid
            User existingUser = userService.findUserByUsername(user.getUsername());
            if (existingUser == null || !existingUser.getPassword().equals(user.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
            }

            // If login is successful, return success message or user data
            return ResponseEntity.ok("Login successful. Redirecting to dashboard...");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to login");
        }
    }


    @DeleteMapping("/users/delete/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        try {
            // Check if user exists
            User userOptional = userService.findUserById(id);
            if (userOptional ==null) {
                return ResponseEntity.notFound().build();
            }

            // Delete user
            userService.deleteUserById(id);
            System.out.println("User deleted successfully");
            return ResponseEntity.ok("User deleted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete user.");
        }
    }


    @PutMapping("/updateUser")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        try {
            // Check if the user exists
            User existingUser = userService.findUserById(user.getId());
            if (existingUser == null) {
                return ResponseEntity.notFound().build();
            }

            // Update user information
            existingUser.setUsername(user.getUsername());
            existingUser.setEmail(user.getEmail());

            // Save the updated user
            userService.saveUser(existingUser);

            // Return success message
            return ResponseEntity.ok("User updated successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update user.");
        }
    }



}
