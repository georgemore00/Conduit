package com.george.conduit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class ProfileNameAlreadyExistsException extends RuntimeException {

    public ProfileNameAlreadyExistsException(String message) {
        super(message);
    }
}
