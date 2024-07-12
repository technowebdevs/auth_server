package com.technoweb.authserver.service.impl;

import com.technoweb.authserver.dto.ChangePasswordDto;
import com.technoweb.authserver.dto.UserDto;
import com.technoweb.authserver.entity.Authority;
import com.technoweb.authserver.entity.User;
import com.technoweb.authserver.exception.UserAlreadyExistException;
import com.technoweb.authserver.repository.UserRepository;
import com.technoweb.authserver.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public Optional<User> createUser(UserDto userDto) {
        Optional<User> checkEmailOrUsername = userRepository.findByEmailOrUsername(userDto.getEmail(), userDto.getUsername());
        if(checkEmailOrUsername.isPresent()){
            throw new UserAlreadyExistException("username or email already registered!");
        }
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        user.setPassword(encodedPassword);
        Set<Authority> authorities = new HashSet<>();
        authorities.add(new Authority("read", user));
        authorities.add(new Authority("write", user));
        user.setAuthorities(authorities);

        return Optional.of(userRepository.save(user));
    }

    @Override
    public String changesPassword(ChangePasswordDto changePasswordDto) {
        String username = changePasswordDto.getUsername();
        List<User> users = userRepository.findByUsername(username);
        if(!users.isEmpty()) {
            if(passwordEncoder.matches(changePasswordDto.getCurrentPassword(), users.get(0).getPassword())) {
                int affectedRow = userRepository.updatePasswordByUsername(username, passwordEncoder.encode(changePasswordDto.getNewPassword()));
                if(affectedRow != 0){
                    return "password changes successfully";
                }
                else{
                    throw new BadCredentialsException("something went wrong");
                }
            }
            else{
                throw new BadCredentialsException("current password is not correct");
            }
        }
        else {
            throw new BadCredentialsException("user not found!");
        }
    }
}
