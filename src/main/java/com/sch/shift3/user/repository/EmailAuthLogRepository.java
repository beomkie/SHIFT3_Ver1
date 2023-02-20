package com.sch.shift3.user.repository;

import com.sch.shift3.user.entity.AccountEmailAuthLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface EmailAuthLogRepository extends JpaRepository<AccountEmailAuthLog, Long> {
    long countByIpAndCreateDateBetween(String ip, LocalDateTime today_begin, LocalDateTime today_end);
}
