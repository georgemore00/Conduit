package com.george.conduit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ArticleNotFoundException extends RuntimeException {

    public ArticleNotFoundException() {
    }

    public ArticleNotFoundException(String message) {
        super(message);
    }
}
