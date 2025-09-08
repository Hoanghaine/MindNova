package com.mindnova.security;


import com.mindnova.entities.Role;
import com.mindnova.entities.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Getter
public class CustomUserDetails implements UserDetails {
    private final Integer id;
    private final String email;
    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(User user, Collection<? extends GrantedAuthority> authorities) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.username = user.getProfile().getFirstName() + " " + user.getProfile().getLastName();
        this.password = user.getPassword();
        this.authorities = authorities;
    }
    public CustomUserDetails(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.username = user.getProfile().getFirstName() + " " + user.getProfile().getLastName();
        this.password = user.getPassword();
        this.authorities = mapRolesToAuthorities(user.getRoles());
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .toList();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override public String getUsername() { return username; }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
