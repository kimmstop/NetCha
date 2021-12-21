package com.example.demo.info;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "user")
@Getter
public class UserInfo implements UserDetails {
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_num")
    private int userNum;
    @Id
    @Column(name = "user_id", unique = true)
    private String id;

    @Column(name = "user_password")
    private String password;

    @Column(name = "user_auth")
    private String auth;

    @Builder
    public UserInfo(int userNum, String user_id, String password, String auth) {
        this.userNum = userNum;
        this.id = user_id;
        this.password = password;
        this.auth = auth;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roles = new HashSet<>();
        for (String role : auth.split(",")) {
            roles.add(new SimpleGrantedAuthority(role));
        }
    return roles;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getUsername() {
        return id;
    }

    @Override
    public String getPassword() {
      return password;
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
