package com.george.conduit.service;

import com.george.conduit.exception.ProfileNotFoundException;
import com.george.conduit.model.Profile;
import com.george.conduit.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    public boolean isProfileNameTaken(String profileName) {
        return profileRepository.findProfileByProfileName(profileName).isPresent();
    }

    public Profile getProfile(String viewer, String profileName) {
        Profile profile = profileRepository.findProfileByProfileName(profileName).orElseThrow(ProfileNotFoundException::new);
        if (viewer != null) {
            Profile theViewer = profileRepository.findProfileByProfileName(viewer).orElseThrow(ProfileNotFoundException::new);
            return profile.viewProfile(theViewer);
        }
        return profile;
    }

    public Profile addFollowToProfile(String viewer, String profileName) {
        Profile profile = profileRepository.findProfileByProfileName(profileName).orElseThrow(ProfileNotFoundException::new);
        Profile viewerProfile = profileRepository.findProfileByProfileName(viewer).orElseThrow(ProfileNotFoundException::new);

        //Making sure that a profile cannot follow itself.
        if (profile.getId().equals(viewerProfile.getId())) {
            return profile;
        } else {
            return profileRepository.save(profile.addFollower(viewerProfile));
        }
    }

    public Profile removeFollowFromProfile(String viewer, String profileName) {
        Profile profile = profileRepository.findProfileByProfileName(profileName).orElseThrow(ProfileNotFoundException::new);
        Profile viewerProfile = profileRepository.findProfileByProfileName(viewer).orElseThrow(ProfileNotFoundException::new);
        return profileRepository.save(profile.removeFollower(viewerProfile));
    }
}
