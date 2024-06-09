package com.mycompany.myapp.dao;

import com.mycompany.myapp.domain.enums.UserStatus;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.io.Serializable;
import java.util.Collection;

@Getter
@Builder
public class CustomUserDetails implements UserDetails, Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String phoneNumber;
    private UserStatus status;

    @Override
    public String getUsername() {
        return String.valueOf(id);
    }

    private Collection<GrantedAuthority> authorities;

    @Override
    public String getPassword() {
        return null;
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
        if (this.status == UserStatus.ACTIVE)
            return true;
        else
            return false;
    }
}
