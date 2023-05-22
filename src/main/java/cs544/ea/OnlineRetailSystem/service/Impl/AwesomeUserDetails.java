package cs544.ea.OnlineRetailSystem.service.Impl;

import com.fasterxml.jackson.annotation.JsonIgnore;

import cs544.ea.OnlineRetailSystem.domain.Role;
import cs544.ea.OnlineRetailSystem.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AwesomeUserDetails implements UserDetails {

    private String emailAddress;
    @JsonIgnore
    private String password;
    private List<Role> roles;

    public AwesomeUserDetails(User user) {
        this.emailAddress = user.getEmail();
        this.password = user.getPassword();
        this.roles = user.getRole();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole().toString()))
                .collect(Collectors.toList());
    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return emailAddress;
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
        return true;
    }
}
