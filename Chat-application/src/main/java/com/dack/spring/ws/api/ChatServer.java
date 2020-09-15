package com.dack.spring.ws.api;

import com.dack.spring.ws.Infrastructure.entities.User;
import com.dack.spring.ws.Infrastructure.repo.Authenication;
import com.dack.spring.ws.Infrastructure.repo.FileHandle;
import com.dack.spring.ws.api.model.Message;
import com.dack.spring.ws.api.model.MessageType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ChatServer extends WebSocketServer {

    private final static Logger logger = LogManager.getLogger(ChatServer.class);

    private HashMap<WebSocket, User> users;
    private Authenication _authenication;
    private Set<WebSocket> conns;

    public ChatServer(int port) {
        super(new InetSocketAddress("localhost",port));
        FileHandle _fileHandle = new FileHandle();
        _authenication = new Authenication(_fileHandle);
        conns = new HashSet<>();
        users = new HashMap<>();
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        conns.add(webSocket);
        _authenication.Initialize();
        logger.info("Connection established from: " + webSocket.getRemoteSocketAddress().getHostString());
        System.out.println("New connection from " + webSocket.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        conns.remove(conn);
        try {
            removeUser(conn);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        logger.info("Connection closed to: " + conn.getRemoteSocketAddress().getHostString());
        System.out.println("Closed connection to " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Message msg = mapper.readValue(message, Message.class);

                switch (msg.getType()) {
                case USER_JOINED:
                    addUser(_authenication.FindUser(msg.getUser().getUsername()), conn);
                    break;
                case USER_LEFT:
                    removeUser(conn);
                    break;
                case TEXT_MESSAGE:
                    broadcastMessage(msg);
            }

            System.out.println("Message from user: " + msg.getUser() + ", text: " + msg.getData() + ", type:" + msg.getType());
            logger.info("Message from user: " + msg.getUser() + ", text: " + msg.getData());
        } catch (IOException e) {
            logger.error("Wrong message format.");
            // return error message to user
        }
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {

        if (conn != null) {
            conns.remove(conn);
        }
        assert conn != null;
        System.out.println("ERROR from " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    @Override
    public void onStart() {

    }

    private void broadcastMessage(Message msg) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String messageJson = mapper.writeValueAsString(msg);
            for (WebSocket sock : conns) {
                sock.send(messageJson);
            }
        } catch (JsonProcessingException e) {
            logger.error("Cannot convert message to json.");
        }
    }

    private void addUser(User user, WebSocket conn) throws JsonProcessingException {
        users.put(conn, user);
        acknowledgeUserJoined(user, conn);
        broadcastUserActivityMessage(MessageType.USER_JOINED);
    }

    private void removeUser(WebSocket conn) throws JsonProcessingException {
        users.remove(conn);
        broadcastUserActivityMessage(MessageType.USER_LEFT);
    }

    private void acknowledgeUserJoined(User user, WebSocket conn) throws JsonProcessingException {
        Message message = new Message();
        message.setType(MessageType.USER_JOINED_ACK);
        message.setUser(user);
        conn.send(new ObjectMapper().writeValueAsString(message));
    }

    private void broadcastUserActivityMessage(MessageType messageType) throws JsonProcessingException {

        Message newMessage = new Message();

        ObjectMapper mapper = new ObjectMapper();
        String data = mapper.writeValueAsString(users.values());
        newMessage.setData(data);
        newMessage.setType(messageType);
        broadcastMessage(newMessage);
    }
}