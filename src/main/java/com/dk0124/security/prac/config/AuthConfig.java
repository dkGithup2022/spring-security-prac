package com.dk0124.security.prac.config;

import com.dk0124.security.prac.config.auth.CustomUserDetailsService;
import com.dk0124.security.prac.config.oauth.Outh2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.header.writers.frameoptions.WhiteListedAllowFromStrategy;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

import java.util.Arrays;


@EnableWebSecurity //spring security filter 를 spring filter chain 를 등록.
public class AuthConfig {

    @Bean
    public PasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Configuration
    @RequiredArgsConstructor
    public static class HttpConfig extends WebSecurityConfigurerAdapter {

        private final CustomUserDetailsService customUserDetailsService;
        private final Outh2UserService outh2UserService;

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(customUserDetailsService);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            enableH2(http);

            //disable csrf
            http.csrf().disable();
            http
                    .authorizeRequests()
                    .antMatchers("/user/**").authenticated()
                    .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                    .anyRequest().permitAll()

                    .and()
                    .formLogin()
                    .loginPage("/loginForm")
                    .loginProcessingUrl("/login")
                    .defaultSuccessUrl("/")


                    .and()
                    .oauth2Login()
                    .loginPage("/loginForm")
                    .userInfoEndpoint()
                    .userService(outh2UserService);

            // 로그인 페이지로 연결이 되는 url.
            // 로그인이 완료되면 액세스 토큰 + 사용자 프로필 정보를 받음.
            // 토큰과 명시된 정보들을 가져오는데 이걸로 조작이 가능한 부분이 있음.
            // 정보가 모자르다면 추가해서 채우기 전까지 중간 단계의 메일로 보유한다던가.
            // gmail 로그인의 결과는 현재 h2에 저장하는데, 이걸 우리의 레포에 저장한다던가.
            // 정보를 가져와서 회원가입을 진행이 필요.
            //  액세스 토큰 + 사용자 프로필 정보 는

            ;
        }

        public void enableH2(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers("/h2-console/**").permitAll()
                    .antMatchers("/h2*/**").permitAll()
                    .and()
                    .csrf().ignoringAntMatchers("/h2*/**")
                    .and()
                    .headers()
                    .addHeaderWriter(
                            new XFrameOptionsHeaderWriter(
                                    new WhiteListedAllowFromStrategy(Arrays.asList("localhost"))    // 여기!
                            )
                    ).frameOptions().sameOrigin()
            ;
        }
    }

}
