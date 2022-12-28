package com.dk0124.security.basic.config.auth.userDetail;

import com.dk0124.security.basic.model.Users;
import com.dk0124.security.basic.repo.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppUserDetailService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> optionalUsers = usersRepository.findByUsername(username);
        if (optionalUsers.isEmpty())
            return null;
        return new AppUserDetail(optionalUsers.get());
    }
}
