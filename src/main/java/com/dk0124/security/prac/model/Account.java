package com.dk0124.security.prac.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class Account  {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String password;

    private String provider;
    private String providerId;

    @Enumerated(EnumType.STRING)
    private Role roles;

}
