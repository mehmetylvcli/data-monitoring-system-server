package com.dataMonitoringSystemServer.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {

    private User user ;
    private String userName;
    private String password;
    private boolean active;

    private List<GrantedAuthority> authorities;

    public UserDetailsImpl(User user) {
        this.userName = user.getUsername() ;
        this.password = user.getPassword();
        this.user = user ;
    }

    public UserDetailsImpl(String userName) {
        this.userName = userName;
    }

    public UserDetailsImpl() {
    }

    public User getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
        return this.authorities ;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.active;
    }



}
