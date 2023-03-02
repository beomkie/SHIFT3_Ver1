package com.sch.shift3.user.repository;

import com.sch.shift3.user.entity.AccountEmailAuthLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface EmailAuthLogRepository extends JpaRepository<AccountEmailAuthLog, Long> {
    Optional<AccountEmailAuthLog> findTop1ByEmailAndCodeAndExpireAtGreaterThanEqual(String email, String code, LocalDateTime expireAt);
    long countByIpAndCreateDateBetween(String ip, LocalDateTime today_begin, LocalDateTime today_end);

    void deleteByEmail(String email);

    @Modifying
    @Query("update AccountEmailAuthLog a set a.expireAt = ?1 where a.email = ?2")
    void updateExpireAt(LocalDateTime yesterday, String username);
}
