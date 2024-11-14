package com.example.librarymanagementsystem.model;

public class Patron {

    private int userId;
    private String username;
    private String email;
    private String password;

    /**
     * Instantiates a new Patron.
     *
     * @param username the username
     * @param email    the email
     * @param password the password
     */
    public Patron(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    /**
     * Instantiates a new Patron.
     *
     * @param userId   the user id
     * @param username the username
     * @param email    the email
     * @param password the password
     */
    public Patron(int userId, String username, String email, String password) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
