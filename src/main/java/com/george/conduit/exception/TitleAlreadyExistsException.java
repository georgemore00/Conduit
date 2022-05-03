package com.george.conduit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class TitleAlreadyExistsException extends RuntimeException {

    public TitleAlreadyExistsException() {
        super();
    }

    public TitleAlreadyExistsException(String message) {
        super(message);
    }
}
