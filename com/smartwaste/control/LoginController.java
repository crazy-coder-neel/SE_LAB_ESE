package com.smartwaste.control;

import com.smartwaste.entity.User;
import com.smartwaste.main.DataStore;
import java.util.ArrayList;

/**
 * LoginController - Control class for user authentication.
 * Validates credentials against stored user data.
 */
public class LoginController {
    private DataStore dataStore;

    public LoginController() {
        this.dataStore = DataStore.getInstance();
    }

    /**
     * Authenticate user by matching username and password.
     * @param username the entered username
     * @param password the entered password
     * @return authenticated User object
     * @throws Exception if credentials are invalid
     */
    public User authenticate(String username, String password) throws Exception {
        if (username == null || username.trim().isEmpty()) {
            throw new Exception("Username cannot be empty.");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new Exception("Password cannot be empty.");
        }

        ArrayList<User> users = dataStore.getUsers();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        throw new Exception("Invalid username or password. Please try again.");
    }
}
