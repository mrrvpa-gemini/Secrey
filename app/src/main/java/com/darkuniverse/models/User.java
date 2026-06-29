package com.darkuniverse.models;

public class User {
    private String username;
    private String password;
    private String role;
    private String expired;

    public User() {}

    public User(String username, String password, String role, String expired) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.expired = expired;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getExpired() { return expired; }
    public void setExpired(String expired) { this.expired = expired; }
}
