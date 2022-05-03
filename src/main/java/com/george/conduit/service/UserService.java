package com.george.conduit.service;

import com.george.conduit.exception.EmailAlreadyExistsException;
import com.george.conduit.exception.ProfileNameAlreadyExistsException;
import com.george.conduit.model.Profile;
import com.george.conduit.model.Role;
import com.george.conduit.model.User;
import com.george.conduit.repository.UserRepository;
import com.george.conduit.security.jwt.JwtHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private JwtHelper jwtHelper;

    //Creates a User and Profile
    public void save(User user, String profileName) {
        if (userRepository.findUserByEmail(user.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email is already taken.");
        }
        if (profileService.isProfileNameTaken(profileName)) {
            throw new ProfileNameAlreadyExistsException("Username is already taken");
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.addRole(new Role("ROLE_USER"));
            Profile profile = new Profile(profileName, user, "no bio added", "no image yet");
            user.setProfile(profile);

            userRepository.save(user);
        }
    }

    public String authenticate(User user) {
        User db_user = userRepository.findUserByEmail(user.getEmail()).orElseThrow(() ->
                new UsernameNotFoundException(String.format("User with email: %s not found.", user.getEmail())));
        if (!passwordEncoder.matches(user.getPassword(), db_user.getPassword())) {
            throw new BadCredentialsException("Incorrect email/password combination.");
        } else {
            return jwtHelper.generateToken(db_user);
        }
    }

    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException(String.format("User with email: %s not found.", email)));
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public User updateUser(Long userId, User newUser) {
        if (userRepository.findUserByEmail(newUser.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email is already taken.");
        }
        if (profileService.isProfileNameTaken(newUser.getProfile().getProfileName())) {
            throw new ProfileNameAlreadyExistsException("Profile name is already taken");
        }
        if (!StringUtils.isEmpty(newUser.getPassword())) {
            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        }
        User currentUser = getUserById(userId);
        User updatedUser = currentUser.updateUser(newUser);
        return userRepository.save(updatedUser);
    }
}
