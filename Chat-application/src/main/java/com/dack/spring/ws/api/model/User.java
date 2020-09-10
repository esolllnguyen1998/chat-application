package com.dack.spring.ws.api.model;

public class User {
    private String Username;
    private String Password;
    private String Fullname;
    private String Nickname;
    private String Email;

    public User() {}

    public User(String username, String password, String fullname, String nickname, String email) {
        Username = username;
        Password = password;
        Fullname = fullname;
        Nickname = nickname;
        Email = email;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }

    public String getNickname() {
        return Nickname;
    }

    public void setNickname(String nickname) {
        Nickname = nickname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
