package com.seindev.sehalsein.restaurantmanagement;

/**
 * Created by sehalsein on 25/02/16.
 */
public class Login {
    private String username;
    private String password;

    public Login() {
    }

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
