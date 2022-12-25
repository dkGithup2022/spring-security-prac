package com.dk0124.security.prac.controller;

import com.dk0124.security.prac.model.Account;
import com.dk0124.security.prac.model.Role;
import com.dk0124.security.prac.model.dto.AccountDTO;
import com.dk0124.security.prac.repo.AccountRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class BasicController {

    private final AccountRepo accountRepo;

    private final PasswordEncoder passwordEncoder;

    @GetMapping("/user")
    public String helloUser() {
        return "Hello user";
    }

    @GetMapping("/admin")
    public String helloAdmin() {
        return "Hello admin";
    }

    @GetMapping({"/loginForm", "/login"})
    public String login() {
        return "loginForm";
    }

    @GetMapping({"/joinForm", "/join"})
    public String join() {
        return "joinForm";
    }

    @PostMapping("/join")
    public String join( AccountDTO accountDTO) {
        accountDTO.setPassword(passwordEncoder.encode(accountDTO.getPassword()));
        Account account = accountDTO.toEntity();
        account.setRoles(Role.USER);
        accountRepo.save(account);
        return "redirect:/loginForm";
    }

    @GetMapping("/joinProc")
    public String joinProc() {
        return "joinProc";
    }

}
