package com.dk0124.security.basic.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    USER("user"), ADMIN("admin");
    private String role;

    public static Role of(String code){
        for(Role r : Role.values()){
            if(code.equals(r) || code.equals(r.getRole()))
                return r;
        }
        return null;
    }
}
