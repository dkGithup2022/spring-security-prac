package com.dk0124.security.prac.config.auth;

import com.dk0124.security.prac.model.Account;
import com.dk0124.security.prac.repo.AccountRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final AccountRepo  accountRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> user =  accountRepo.findByUsername(username);
        if(user.isEmpty())
            return null;

        return new CustomUserDetails(user.get());
    }

}


