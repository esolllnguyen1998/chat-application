package com.dack.spring.ws.api.model;

public class FileModel {
    private String ID;
    private String FileName;

    public FileModel(String id, String fileName) {
        ID = id;
        FileName = fileName;
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
}
