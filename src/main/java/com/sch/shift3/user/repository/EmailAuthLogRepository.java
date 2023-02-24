package com.sch.shift3.user.repository;

import com.sch.shift3.user.entity.AccountEmailAuthLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface EmailAuthLogRepository extends JpaRepository<AccountEmailAuthLog, Long> {
    Optional<AccountEmailAuthLog> findByEmailAndCodeAndExpireAtGreaterThanEqual(String email, String code, LocalDateTime expireAt);
    long countByIpAndCreateDateBetween(String ip, LocalDateTime today_begin, LocalDateTime today_end);
}
