package com.sch.shift3.user.repository;

import com.sch.shift3.user.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer>{
    Optional<Account> findByUsername(String username);

    Optional<Account> findByUsernameAndName(String username, String name);
}
