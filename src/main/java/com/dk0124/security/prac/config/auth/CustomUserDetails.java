package com.dk0124.security.prac.config.auth;

// security 에 login 요청이 오면 인터셉터측에서 스프링 세큐리티의 로그인 요청으로 진행
// 로그인이 완성되면 security session 이 쓰레드 로컬이 저장
// 오브젝트 타입 = Authentication 객체
// Authentication 의 유저 정보는 UserDetails 임.
// UserDetail 형시으로 만들어주는 부분에 대한 작업이 필요

import com.dk0124.security.prac.model.Account;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


//auth context object
@AllArgsConstructor
public class CustomUserDetails implements UserDetails , OAuth2User {    // Authentication 객체 통일
    private  Account account;
    private  Map<String,Object> attributes = new HashMap<String,Object>();

    public CustomUserDetails(Account account) {
        this.account = account;
    }

    @Override
    public <A> A getAttribute(String name) {
        return OAuth2User.super.getAttribute(name);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(() -> "ROLE_" + account.getRoles()); // 여기는 ROLE_ 붙여야 됨
        return authorities;
    }

    @Override
    public String getPassword() {
        return account.getPassword();
    }

    @Override
    public String getUsername() {
        return account.getUsername();
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

    @Override
    public String getName() {
        return account.getUsername();
    }
}
