package com.george.conduit.dto.article;

import com.george.conduit.model.Profile;

public class AuthorResponse {

    private String username;
    private String bio;
    private String image;
    private boolean following;

    public AuthorResponse(Profile profile) {
        this.username = profile.getProfileName();
        this.bio = profile.getBio();
        this.image = profile.getImage_url();
        this.following = profile.getIsFollowing();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isFollowing() {
        return following;
    }

    public void setFollowing(boolean following) {
        this.following = following;
    }
}
