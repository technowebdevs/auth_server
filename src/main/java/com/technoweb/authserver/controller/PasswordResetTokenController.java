package com.technoweb.authserver.controller;

import com.technoweb.authserver.service.impl.PasswordResetTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PasswordResetTokenController {


    @Autowired
    private PasswordResetTokenService passwordResetTokenService;
    @GetMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam("username") String username){
        String message = passwordResetTokenService.resetPassword(username);

        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
}
