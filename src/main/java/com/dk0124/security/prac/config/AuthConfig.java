package com.dk0124.security.prac.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.header.writers.frameoptions.WhiteListedAllowFromStrategy;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

import java.util.Arrays;



@EnableWebSecurity //spring security filter 를 spring filter chain 를 등록.
public class AuthConfig {
    // iniaital action
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(userDetails);
    }

    @Configuration
    public static class HttpConfig extends WebSecurityConfigurerAdapter{
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            enableH2(http);

            //disable csrf
            http.csrf().disable();
            http.authorizeRequests()
                    .antMatchers("/user/**").authenticated()
                    .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
                    .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                    .anyRequest().permitAll()
                    .and()
            .formLogin()
                .loginPage("/login")
                .failureForwardUrl("/login2")
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
