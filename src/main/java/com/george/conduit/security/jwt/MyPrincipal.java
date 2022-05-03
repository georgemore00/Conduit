package com.george.conduit.security.jwt;

import java.io.Serializable;

public class MyPrincipal implements Serializable {
    private final Long userId;
    private final String profileName;

    public MyPrincipal(Long userId, String profileName) {
        this.userId = userId;
        this.profileName = profileName;
    }

    public Long getUserId() {
        return userId;
    }

    public String getProfileName() {
        return profileName;
    }

    @Override
    public String toString() {
        return "MyPrincipal{" +
                "userId=" + userId +
                ", username='" + profileName + '\'' +
                '}';
    }
}
