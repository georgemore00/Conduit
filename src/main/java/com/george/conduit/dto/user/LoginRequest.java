package com.george.conduit.dto.user;

import com.george.conduit.model.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class LoginRequest {

    @Email(message = "Please provide a valid Email.")
    @NotBlank
    private String email;

    @NotBlank(message = "Password cannot be blank.")
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User toUserEntity() {
        return new User(getPassword(), getEmail());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
