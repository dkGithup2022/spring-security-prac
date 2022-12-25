package com.dk0124.security.prac.repo;

import com.dk0124.security.prac.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepo extends JpaRepository<Account,Long> {

    Optional<Account> findByName(String name);
}
