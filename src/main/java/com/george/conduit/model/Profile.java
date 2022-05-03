package com.george.conduit.model;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String profileName;
    private String bio;
    private String image_url;

    @Transient
    private Boolean isFollowing = false;

    @OneToOne()
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(inverseJoinColumns = {@JoinColumn(name = "follower_id")})
    private Set<Profile> followers = new HashSet<>();

    public Profile() {
    }

    public Profile(String profileName, User user, String bio, String image_url) {
        this.profileName = profileName;
        this.user = user;
        this.bio = bio;
        this.image_url = image_url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean getIsFollowing() {
        return isFollowing;
    }

    public void setIsFollowing(Boolean following) {
        isFollowing = following;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Profile> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<Profile> followers) {
        this.followers = followers;
    }

    public Profile addFollower(Profile follower) {
        this.followers.add(follower);
        this.setIsFollowing(true);
        return this;
    }

    public Profile removeFollower(Profile follower) {
        this.followers.remove(follower);
        this.setIsFollowing(false);
        return this;
    }

    public void updateProfile(Profile newProfile) {
        if (!StringUtils.isEmpty(newProfile.getProfileName())) {
            this.setProfileName(newProfile.getProfileName());
        }
        this.setBio(newProfile.getBio());
        this.setImage_url(newProfile.getImage_url());
    }

    public Profile viewProfile(Profile viewer) {
        if (this.followers.contains(viewer)) {
            setIsFollowing(true);
        }
        return this;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", profileName='" + profileName + '\'' +
                ", bio='" + bio + '\'' +
                ", image_url='" + image_url + '\'' +
                ", isFollowing=" + isFollowing +
                ", user=" + user +
                ", followers=" + followers +
                '}';
    }
}
