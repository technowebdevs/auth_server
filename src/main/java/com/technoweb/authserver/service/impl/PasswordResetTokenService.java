package com.technoweb.authserver.service.impl;

import com.technoweb.authserver.entity.PasswordResetToken;
import com.technoweb.authserver.entity.User;
import com.technoweb.authserver.repository.PasswordResetTokenRepository;
import com.technoweb.authserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

@Service
public class PasswordResetTokenService {

    @Autowired
    private PasswordResetTokenEmailService passwordResetTokenEmailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();


    public String resetPassword(String username) {

        List<User> users = userRepository.findByUsername(username);
        if(!users.isEmpty()) {
            User user = users.get(0);
            PasswordResetToken passwordResetToken = new PasswordResetToken();
            passwordResetToken.setUserId(user.getId());
            passwordResetToken.setUsed(false);
            passwordResetToken.setToken(generateToken());
            passwordResetToken.setExpiryDate(getExpiryTime());

            passwordResetTokenRepository.save(passwordResetToken);

            if(passwordResetTokenEmailService.sendPasswordResetEmail(passwordResetToken, user.getEmail(), username)) {
                return "a link to reset your password has been successfully sent to your mail "+maskEmail(user.getEmail());
            }
            else{
                throw new BadCredentialsException("Error while sending reset link");
            }

        }
        else{
            throw new BadCredentialsException("user with username: "+username+" is not registered");
        }
    }

    public static String maskEmail(String email) {
        int atIndex = email.indexOf("@");
        if (atIndex <= 2) {
            return email;
        }

        String namePart = email.substring(0, atIndex);
        String domainPart = email.substring(atIndex);

        int startChars = 3;
        int endChars = 3;

        if (namePart.length() <= startChars + endChars) {
            return namePart + domainPart;
        }

        String maskedMiddle = "*".repeat(namePart.length() - startChars - endChars);

        String maskedNamePart = namePart.substring(0, startChars) + maskedMiddle + namePart.substring(namePart.length() - endChars);

        return maskedNamePart + domainPart;
    }


    private String generateToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    private LocalDateTime getExpiryTime() {
        return LocalDateTime.now().plusHours(24);
    }
}
