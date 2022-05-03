package com.george.conduit.dto.comment;


import javax.validation.constraints.NotBlank;

public class CreateCommentRequest {

    @NotBlank(message = "body cannot be blank.")
    private String body;

    public CreateCommentRequest() {
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
