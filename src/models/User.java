package models;

import exceptions.LoginFailedError;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class User {
    private static final List<User> users = new ArrayList<>();
    private final String username;
    private final String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        users.add(this);
    }

    public static User login(String username, String password) throws LoginFailedError {
        for (User user : users) {
            if (user.username.equals(username) && user.password.equals(password)) {
                return user;
            }
        }
        throw new LoginFailedError("Login failed: Invalid username or password.");
    }

    public String get_username() {
        return username;
    }

    public abstract void options(Scanner scanner);
}