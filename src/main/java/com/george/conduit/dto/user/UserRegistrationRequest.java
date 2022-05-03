package com.george.conduit.dto.user;

import com.george.conduit.model.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserRegistrationRequest {

    @NotBlank(message = "Profile name cannot be blank.")
    private String profileName;

    @NotBlank(message = "Password cannot be blank.")
    private String password;

    @Email(message = "Please provide a valid Email.")
    @NotBlank
    private String email;

    public UserRegistrationRequest(String profileName, String password, String email) {
        this.profileName = profileName;
        this.password = password;
        this.email = email;
    }

    public User toUserEntity() {
        return new User(getPassword(), getEmail());
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
