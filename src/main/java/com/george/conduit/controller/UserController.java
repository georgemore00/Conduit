package com.george.conduit.controller;

import com.george.conduit.dto.user.CurrentUserResponse;
import com.george.conduit.dto.user.LoginRequest;
import com.george.conduit.dto.user.UpdateUserRequest;
import com.george.conduit.dto.user.UserRegistrationRequest;
import com.george.conduit.model.User;
import com.george.conduit.security.jwt.JwtAuthentication;
import com.george.conduit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/api/v1/users")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public ResponseEntity<Void> register(@Valid @RequestBody UserRegistrationRequest request) {
        User newUser = request.toUserEntity();
        userService.save(newUser, request.getProfileName());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> authenticate(@Valid @RequestBody LoginRequest request) {
        User user = request.toUserEntity();
        String jwt = userService.authenticate(user);

        User currentUser = userService.getUserByEmail(request.getEmail());
        return new ResponseEntity<>(new CurrentUserResponse(currentUser, jwt), HttpStatus.OK);
    }

    @RequestMapping(path = "/my-user", method = RequestMethod.GET)
    public ResponseEntity<?> getCurrentUser(JwtAuthentication authentication) {
        Long currentUserId = authentication.getPrincipal().getUserId();
        String jwt = authentication.getCredentials().toString();

        User currentUser = userService.getUserById(currentUserId);
        return new ResponseEntity<>(new CurrentUserResponse(currentUser, jwt), HttpStatus.OK);
    }

    @RequestMapping(path = "/my-user", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(JwtAuthentication authentication, @RequestBody UpdateUserRequest request) {
        Long currentUserId = authentication.getPrincipal().getUserId();
        String jwt = authentication.getCredentials().toString();

        User newUser = request.toUserEntity();
        User updatedUser = userService.updateUser(currentUserId, newUser);
        return new ResponseEntity<>(new CurrentUserResponse(updatedUser, jwt), HttpStatus.OK);
    }
}
