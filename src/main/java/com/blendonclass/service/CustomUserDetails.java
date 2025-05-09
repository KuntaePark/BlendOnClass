package com.blendonclass.service;

import com.blendonclass.constant.ROLE;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
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
    private Collection<GrantedAuthority> authorities;

    public CustomUserDetails() {
        this.authorities = new ArrayList<>();
    }

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

    public void setRole(ROLE role) {
        this.authorities.add(new SimpleGrantedAuthority(role.getRole()));
        this.role = role;
    }
}
