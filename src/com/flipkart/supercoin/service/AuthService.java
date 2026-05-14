package com.flipkart.supercoin.service;

import com.flipkart.supercoin.exception.InvalidOperationException;
import com.flipkart.supercoin.model.User;
import com.flipkart.supercoin.repository.UserRepository;

public class AuthService {

    private User currentUser;
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void login(String userName) {

        if (currentUser != null) {
            throw new InvalidOperationException("Another user already logged in");
        }

        User user = userRepository.findByName(userName);

        if (user == null) {
            throw new InvalidOperationException("User not found");
        }

        currentUser = user;

        System.out.println(userName + " logged in successfully");
    }

    public void logout() {

        if (currentUser == null) {
            throw new InvalidOperationException("No user logged in");
        }

        System.out.println(currentUser.getName() + " logged out");

        currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
