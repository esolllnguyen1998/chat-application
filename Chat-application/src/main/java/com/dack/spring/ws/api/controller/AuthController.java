package com.dack.spring.ws.api.controller;

import com.dack.spring.ws.Infrastructure.repo.Authenication;
import com.dack.spring.ws.Infrastructure.repo.FileHandle;
import com.dack.spring.ws.Infrastructure.entities.User;
import com.dack.spring.ws.api.model.LoginModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

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
    @ResponseBody
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<User> login(@RequestBody LoginModel user) throws IOException {
        User userLogged = _authenication.Login(user);

        if(userLogged != null) {
            return ResponseEntity.ok(userLogged);
        }
        return ResponseEntity.badRequest().build();
    }
}
