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
public class ChangePasswordDto {

    @NotEmpty(message = "username must not be empty")
    @Size(min = 6, max = 14, message = "username must be longer than 5 character and shorter than 15 character")
    private String username;

    @NotEmpty(message = "username must not be empty")
    @Size(min = 5, max = 15, message = "password must be between 5 to 15 characters")
    private String currentPassword;

    @NotEmpty(message = "username must not be empty")
    @Size(min = 5, max = 15, message = "password must be between 5 to 15 characters")
    private String newPassword;
}
