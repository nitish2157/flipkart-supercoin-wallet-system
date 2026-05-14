package com.flipkart.supercoin.repository;

import com.flipkart.supercoin.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserRepository {

    private final Map<String, User> userDb = new HashMap<>();

    public void save(User user) {
        userDb.put(user.getName(), user);
    }

    public User findByName(String name) {
        return userDb.get(name);
    }
}
