package com.ai_notes_app.demo.service;

import java.util.List;
import java.util.Optional;

import com.ai_notes_app.demo.model.User;

public interface UserService {

    List<User> getUsers();

    Optional<User> getUserByUsername(String username);

    Long getUserIdByUsername(String username);

    boolean hasUserWithUsername(String username);

    boolean hasUserWithEmail(String email);

    User validateAndGetUserByUsername(String username);

    User saveUser(User user);

    void deleteUser(User user);

    Optional<User> validUsernameAndPassword(String username, String password);
}
