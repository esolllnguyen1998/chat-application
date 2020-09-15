package com.dack.spring.ws.Infrastructure.repo;

import com.dack.spring.ws.Infrastructure.entities.User;
import com.dack.spring.ws.api.model.LoginModel;

import java.util.ArrayList;

public class Authenication {

    private FileHandle _fileHandle;
    ArrayList<User> listUsers;

    public Authenication(FileHandle fileHandle)
    {
        _fileHandle = fileHandle;
    }

    public void Initialize()
    {
        ArrayList<String> strUsers = _fileHandle.ReadAllFile();
        listUsers = RetrieveUsersByStringBuilder(strUsers);
    }

    public User Login(LoginModel model) {
        User user = FindUser(model.getUsername());
        if(user != null && model.getPassword().equals(user.getPassword())) {
            return user;
        }

        return null;
    }

    public User FindUser(String username) {
        for (User user : listUsers) {
            if (username.equals(user.getUsername()))
                return user;
        }

        return null;
    }

    private ArrayList<User> RetrieveUsersByStringBuilder(ArrayList<String> strUsers) {
        var users = new ArrayList<User>();
        for (String strStudent : strUsers) {
            User user = BinaryToUser(strStudent);
            users.add(user);
        }

        return users;
    }

    private User BinaryToUser(String strUser) {
        var infors = strUser.split(",");
        return new User(infors[0], infors[1], infors[2], infors[3], infors[4]);
    }
}
