package com.technoweb.authserver.controller;

import com.technoweb.authserver.dto.UserDto;
import com.technoweb.authserver.entity.User;
import com.technoweb.authserver.exception.UserNotCreatedException;
import com.technoweb.authserver.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(path = "/auth", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class AuthServerController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @PostMapping("/signup")
    public ResponseEntity<String> createUser(@Valid @RequestBody UserDto user) {
        System.out.println(userServiceImpl);
        Optional<User> savedUser = userServiceImpl.createUser(user);

        return savedUser
                .map(u -> ResponseEntity.status(HttpStatus.CREATED).body("User with username "+ u.getUsername()+" created successfully"))
                .orElseThrow(() ->new UserNotCreatedException("Error while signing up"));

    }


}
