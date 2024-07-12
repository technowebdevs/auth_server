package com.technoweb.authserver.service;

import com.technoweb.authserver.dto.ChangePasswordDto;
import com.technoweb.authserver.dto.UserDto;
import com.technoweb.authserver.entity.User;

import java.util.Optional;

public interface IUserService {
    Optional<User> createUser(UserDto userDto);
    String changesPassword(ChangePasswordDto changePasswordDto);
}
