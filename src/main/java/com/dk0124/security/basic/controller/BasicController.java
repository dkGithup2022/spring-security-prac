package com.dk0124.security.basic.controller;

import com.dk0124.security.basic.dto.JoinDTO;
import com.dk0124.security.basic.repo.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class BasicController {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/empty")
    public String index() {
        return "index";
    }

    @GetMapping({"/join", "/joinForm"})
    public String getJoin() {
        return "joinForm";
    }

    @GetMapping({"/login", "/loginForm"})
    public String login() {
        return "loginForm";
    }

    @PostMapping({"/join", "/joinForm"})
    public String postJoin(JoinDTO joinDTO) {

        if (!isValidJoinDTO(joinDTO))
            return "joinForm";

        Optional user = usersRepository.findByUsername(joinDTO.getUsername());

        if(user.isPresent())
            return "loginForm";

        joinDTO.setPassword(passwordEncoder.encode(joinDTO.getPassword()));
        usersRepository.save(joinDTO.toEntity());

        return "loginForm";
    }

    private boolean isValidJoinDTO(JoinDTO joinDTO) {
        return (joinDTO != null
                && joinDTO.getUsername() != null && !joinDTO.getUsername().isBlank())
                && (joinDTO.getPassword() != null && !joinDTO.getPassword().isBlank());
    }
}
