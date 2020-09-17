package com.dack.spring.ws.api.model;

import com.dack.spring.ws.Infrastructure.entities.User;

public class FileModel {
    private String ID;
    private String FileName;
    private String URL;
    private User User;
    private Long Size;

    public FileModel(String id, String fileName, String url, com.dack.spring.ws.Infrastructure.entities.User user, Long size) {
        ID = id;
        FileName = fileName;
        URL = url;
        User = user;
        Size = size;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public User getUser() {
        return User;
    }

    public void setUser(User user) {
        User = user;
    }

    public Long getSize() {
        return Size;
    }

    public void setSize(Long size) {
        Size = size;
    }
}
