package com.dack.spring.ws.api.model;

import com.dack.spring.ws.Infrastructure.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message implements Serializable{
    private User user;
    private MessageType type;
    private String data;
    private FileModel filemodel;
}