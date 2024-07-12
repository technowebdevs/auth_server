package com.technoweb.authserver.service.impl;

import com.technoweb.authserver.entity.PasswordResetToken;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class PasswordResetTokenEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public boolean sendPasswordResetEmail(PasswordResetToken passwordResetToken, String email, String username) {
        String resetUrl = "http://localhost:8080/reset-technoweb-password?token=" + passwordResetToken.getToken();

        String subject = "TechnoWeb Password Reset";

        String content = getEmailContent(username, resetUrl);

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(content, true); // true indicates the text is HTML

            javaMailSender.send(message);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Error Sending Email: " + e.getMessage());
        }
    }

    private String getEmailContent(String username, String resetUrl){
        // Example logo URL; replace with your actual logo URL
        String logoUrl = "https://tech.technowebsolutions.in/wp-content/uploads/2023/05/website_one-e1684483537251.png";

        String content = "<div style='font-family: Arial, sans-serif; color: #333;'>"
                + "<div style='text-align: center; padding: 10px;'>"
                + "<img src='" + logoUrl + "' alt='TechnoWeb Logo' style='width: 150px; height: auto;'/>"
                + "</div>"
                + "<div style='background-color: #f4f4f4; padding: 20px; border-radius: 8px;'>"
                + "<h2 style='color: #0066cc;'>Password Reset Request</h2>"
                + "<p>Hello, " + username + "</p>"
                + "<p>You have requested to reset your password. Click the link below to reset it:</p>"
                + "<p><a href='" + resetUrl + "' style='display: inline-block; padding: 10px 20px; font-size: 16px; color: #ffffff; background-color: #0066cc; text-decoration: none; border-radius: 5px;'>Reset Password</a></p>"
                + "<p style='color: #777;'>If you did not request a password reset, please ignore this email.</p>"
                + "<p>Best regards,<br>TechnoWeb Team</p>"
                + "</div>"
                + "</div>";

        return content;
    }

}
