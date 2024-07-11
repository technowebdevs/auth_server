package com.technoweb.authserver.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserNotCreatedException extends RuntimeException{

    public UserNotCreatedException(String message){
        super(message);
    }
}
