package com.george.conduit.utils;

import com.george.conduit.security.jwt.MyPrincipal;

public final class CurrentUserInfo {

    public static String getOptionalCurrentUser(MyPrincipal principal) {
        if (principal != null) {
            return principal.getProfileName();
        }
        return null;
    }
}
