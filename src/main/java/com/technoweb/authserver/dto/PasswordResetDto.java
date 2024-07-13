package com.technoweb.authserver.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetDto {

    @NotEmpty(message = "username must not be empty")
    @Size(min = 5, max = 15, message = "password must be between 5 to 15 characters")
    private String password;
}
