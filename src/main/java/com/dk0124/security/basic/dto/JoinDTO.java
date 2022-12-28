package com.dk0124.security.basic.dto;

import com.dk0124.security.basic.model.Role;
import com.dk0124.security.basic.model.Users;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class JoinDTO {
    private String username;
    private String password;
    private String role;

    public Users toEntity(){
        return Users.builder()
                .username(username)
                .password(password)
                .role(Role.of(role))
                .build();
    }
}
