package com.doci.webPrj.config;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

@Setter
@Getter
@Builder
public class MyUserDetails implements UserDetails, OAuth2User {
    private int id;
    private String userId;
    private String email;
    private String name;
    private String nickname;
    private String pwd;
    private String profileImage;
    private Date regDate;
    private String roles;


    private String provider;
    private String providerId;
    private Map<String, Object> attributes;
    @Override

    public Map<String, Object> getAttributes() {
        System.out.println("어트리뷰트");
        return attributes;
    }

    @Override
    //return 결과가 CustomAuthenticationSuccessHandler 에 onAuthenticationSuccess함수로 전달 된다.
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + roles));  //ROLE_ 을 붙여야 SpringSecurity에서 hasRoles에서 인식함
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.pwd;
    }

    @Override
    public String getUsername() {
        return this.userId;
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
