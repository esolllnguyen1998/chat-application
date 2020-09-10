package com.dack.spring.ws.api.controller;

import com.dack.spring.ws.api.model.ChatMessage;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class AuthController {

    @SendTo("/topic/public")
    public ChatMessage regisger(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor)
    {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage)
    {
        return chatMessage;
    }
}
