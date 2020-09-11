package com.dack.spring.ws.api.controller;

import com.dack.spring.ws.Infrastructure.repo.Authenication;
import com.dack.spring.ws.Infrastructure.repo.FileHandle;
import com.dack.spring.ws.Infrastructure.entities.User;
import com.dack.spring.ws.api.model.LoginModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

@Controller
public class AuthController {
    private FileHandle _fileHandle;
    private Authenication _authenication;

    public AuthController()
    {
        _fileHandle = new FileHandle();
        _authenication = new Authenication(_fileHandle);
        _authenication.Initialize();
    }

    @PostMapping("/auth/register")
    public ResponseEntity regisger(@RequestBody User user) throws IOException {
        _fileHandle.WriteUserToFile(user, false );
        return ResponseEntity.ok().build();
    }

    @PostMapping("/auth/login")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity login(@RequestBody LoginModel user) throws IOException {
        boolean isLoggedIn = _authenication.Login(user);

        if(isLoggedIn) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
