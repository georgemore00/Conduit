package com.george.conduit.controller;

import com.george.conduit.dto.profile.ProfileResponse;
import com.george.conduit.security.jwt.MyPrincipal;
import com.george.conduit.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.george.conduit.utils.CurrentUserInfo.getOptionalCurrentUser;

@RequestMapping(path = "api/v1/profiles")
@RestController
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @RequestMapping(path = "/{profileName}", method = RequestMethod.GET)
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal MyPrincipal principal, @PathVariable String profileName) {
        String viewer = getOptionalCurrentUser(principal);
        ProfileResponse profileResponse = new ProfileResponse(profileService.getProfile(viewer, profileName));
        return new ResponseEntity<>(profileResponse, HttpStatus.OK);
    }

    @RequestMapping(path = "/{profileName}/follow", method = RequestMethod.POST)
    public ResponseEntity<?> addFollowToProfile(@AuthenticationPrincipal MyPrincipal principal, @PathVariable String profileName) {
        String viewer = principal.getProfileName();
        ProfileResponse profileResponse = new ProfileResponse(profileService.addFollowToProfile(viewer, profileName));
        return new ResponseEntity<>(profileResponse, HttpStatus.CREATED);
    }

    @RequestMapping(path = "/{profileName}/follow", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeFollowFromProfile(@AuthenticationPrincipal MyPrincipal principal, @PathVariable String profileName) {
        String viewer = principal.getProfileName();
        ProfileResponse profileResponse = new ProfileResponse(profileService.removeFollowFromProfile(viewer, profileName));
        return new ResponseEntity<>(profileResponse, HttpStatus.ACCEPTED);
    }
}
