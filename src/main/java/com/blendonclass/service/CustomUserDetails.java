package com.blendonclass.service;

import com.blendonclass.constant.ROLE;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter@Setter
public class CustomUserDetails implements UserDetails {
    private static final long serialVersionUID = 1L;
    private String id;
    private String loginId;
    private String password;
    private String email;
    private String name;
    private ROLE role;
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return id;
    }
}
