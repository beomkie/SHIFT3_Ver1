package com.sch.shift3.user.repository;

import com.sch.shift3.user.entity.AccountPasswordChange;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountPasswordChangeRepository extends JpaRepository<AccountPasswordChange, Integer> {
    Optional<AccountPasswordChange> findByUsername(String username);

    Optional<AccountPasswordChange> findByHash(String hash);
}
