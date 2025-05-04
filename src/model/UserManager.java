package model;

import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private List<User> users = new ArrayList<>();
    private User currentUser = null;
    private static int nextUserId = 1;

    public UserManager() {
        // Add a default admin user
        registerUser("admin", "admin", "admin@parking.com", "admin");
    }

    public User registerUser(String username, String password, String email, String userType) {
        // Check if username already exists
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return null; // Username already exists
            }
        }

        // Create new user
        User newUser = new User(nextUserId++, username, password, email, userType);
        users.add(newUser);
        return newUser;
    }

    public User login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                currentUser = user;
                return user;
            }
        }
        return null; // Authentication failed
    }

    public void logout() {
        currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users); // Return a copy to prevent modification
    }
}