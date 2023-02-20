package com.sch.shift3.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account_email_auth_log")
@EntityListeners(AuditingEntityListener.class)
public class AccountEmailAuthLog {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false)
        private Integer id;

        @Column(name = "email", nullable = false, updatable = false)
        private String email;

        @Column(name = "code", nullable = false, updatable = false)
        private String code;

        @CreatedDate
        @Column(name = "created_at", updatable = false)
        private LocalDateTime createDate;

        @Column(name = "expired_at", updatable = false)
        private LocalDateTime expireAt;

        @Column(name = "ip", nullable = true, updatable = false)
        private String ip;
    }
