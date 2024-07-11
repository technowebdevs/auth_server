package com.technoweb.authserver.service.impl;

import com.technoweb.authserver.dto.UserDto;
import com.technoweb.authserver.entity.User;
import com.technoweb.authserver.exception.UserAlreadyExistException;
import com.technoweb.authserver.repository.UserRepository;
import com.technoweb.authserver.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public Optional<User> createUser(UserDto userDto) {
        Optional<User> checkEmailOrUsername = userRepository.findByEmailOrUsername(userDto.getEmail(), userDto.getUsername());
//        Optional<User> checkUser = userRepository.findByUsername(userDto.getUsername());
        if(checkEmailOrUsername.isPresent()){
            throw new UserAlreadyExistException("username or email already registered!");
        }
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        user.setPassword(encodedPassword);

        return Optional.of(userRepository.save(user));
    }
}
