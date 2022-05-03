package com.george.conduit.dto.profile;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.george.conduit.model.Profile;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.WRAPPER_OBJECT;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

@JsonTypeName("profile")
@JsonTypeInfo(include = WRAPPER_OBJECT, use = NAME)
public class ProfileResponse {

    private String profileName;
    private String bio;
    private String image;
    private boolean following;

    public ProfileResponse(Profile profile) {
        this.profileName = profile.getProfileName();
        this.bio = profile.getBio();
        this.image = profile.getImage_url();
        this.following = profile.getIsFollowing();
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
