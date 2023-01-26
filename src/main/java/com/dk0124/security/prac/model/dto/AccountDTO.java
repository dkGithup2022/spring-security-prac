package com.dk0124.security.prac.model.dto;


import com.dk0124.security.prac.model.Account;
import com.dk0124.security.prac.model.Role;
import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class AccountDTO {
    private String username;
    private String password;
    private String role;

    public Account toEntity() {

        return Account.builder()
                .username(username)
                .password(this.password)
                .roles(Role.of(role))
                .build();
    }
}
