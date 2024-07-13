package com.technoweb.authserver.controller;

import com.technoweb.authserver.dto.PasswordResetDto;
import com.technoweb.authserver.service.impl.PasswordResetTokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PasswordResetTokenController {


    @Autowired
    private PasswordResetTokenService passwordResetTokenService;

    @GetMapping("/reset-password")
    public ResponseEntity<String> generateResetPasswordLink(@RequestParam("username") String username){
        String message = passwordResetTokenService.resetPassword(username);

        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @PatchMapping("/reset-technoweb-password")
    public ResponseEntity<String> resetPassword(@RequestParam("token") String token, @Valid @RequestBody PasswordResetDto passwordResetDto) {
        String message = passwordResetTokenService.resetPasswordUsingToken(passwordResetDto.getPassword(), token);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
}
