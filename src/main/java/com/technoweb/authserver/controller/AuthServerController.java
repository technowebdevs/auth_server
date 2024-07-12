package com.technoweb.authserver.controller;

import com.technoweb.authserver.dto.ChangePasswordDto;
import com.technoweb.authserver.dto.UserDto;
import com.technoweb.authserver.entity.User;
import com.technoweb.authserver.exception.UserNotCreatedException;
import com.technoweb.authserver.repository.UserRepository;
import com.technoweb.authserver.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
//@RequestMapping(path = "/auth", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class AuthServerController {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/user")
    public User getUserDetailsAfterLogin(Authentication authentication) {
        List<User> users = userRepository.findByUsername(authentication.getName());
        if (users.size() > 0) {
            return users.get(0);
        } else {
            return null;
        }

    }

    @PostMapping("/signup")
    public ResponseEntity<String> createUser(@Valid @RequestBody UserDto user) {
        Optional<User> savedUser = iUserService.createUser(user);

        return savedUser
                .map(u -> ResponseEntity.status(HttpStatus.CREATED).body("User with username "+ u.getUsername()+" created successfully"))
                .orElseThrow(() ->new UserNotCreatedException("Error while signing up"));

    }

    @PatchMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        String successMessage = iUserService.changesPassword(changePasswordDto);
        return ResponseEntity.status(HttpStatus.OK).body(successMessage);
    }

}
