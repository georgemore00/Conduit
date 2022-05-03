package com.george.conduit.model;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String password;
    private String email;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Profile profile;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Role> roles = new ArrayList<>();

    public User() {
    }

    public User(String password, String email) {
        this.password = password;
        this.email = email;
    }

    public User(String password, String email, Profile profile) {
        this.password = password;
        this.email = email;
        this.profile = profile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public boolean addRole(Role role) {
        return getRoles().add(role);
    }

    public User updateUser(User newUser) {
        if (!StringUtils.isEmpty(newUser.getEmail())) {
            this.setEmail(newUser.getEmail());
        }
        if (!StringUtils.isEmpty(newUser.getPassword())) {
            this.setPassword(newUser.getPassword());
        }
        this.getProfile().updateProfile(newUser.getProfile());
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", profile=" + profile +
                ", roles=" + roles +
                '}';
    }
}
