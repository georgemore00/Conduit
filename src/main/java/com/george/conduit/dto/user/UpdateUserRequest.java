package com.george.conduit.dto.user;

import com.george.conduit.model.Profile;
import com.george.conduit.model.User;

public class UpdateUserRequest {

    private String email;
    private String password;
    private String profileName;
    private String bio;
    private String image_url;

    public UpdateUserRequest(String email, String password, String profileName, String bio, String image) {
        this.email = email;
        this.password = password;
        this.profileName = profileName;
        this.bio = bio;
        this.image_url = image;
    }

    private Profile toProfileEntity() {
        return new Profile(getProfileName(), null, getBio(), getImage_url());
    }

    public User toUserEntity() {
        return new User(getPassword(), getEmail(), toProfileEntity());
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

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    @Override
    public String toString() {
        return "UpdateUserRequest{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", username='" + profileName + '\'' +
                ", bio='" + bio + '\'' +
                ", image_url='" + image_url + '\'' +
                '}';
    }
}
