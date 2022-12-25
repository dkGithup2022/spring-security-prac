package com.dk0124.security.prac.model.dto;


import com.dk0124.security.prac.model.Account;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Builder
@Data
public class AccountDTO {
    private String username;
    private String password;

    public Account toEntity(){
        return Account.builder()
                .username(username)
                .password(this.password)
                .build();
    }
}
