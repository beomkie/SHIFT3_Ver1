package com.sch.shift3.user.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "account_password_change")
public class AccountPasswordChange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "hash", nullable = false, length = 100)
    private String hash;

    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Column(name = "expired_at", nullable = false)
    private LocalDateTime expiredAt;

    public AccountPasswordChange(String username) {
        this.username = username;

        // UUID 생성
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();

        // SHA-256 해시
        this.hash = getSha256(uuidString);

        // expiredAt Today
        this.expiredAt = LocalDateTime.now().plusDays(1);
    }

    private String getSha256(String str) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(str.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("암호화 에러. 새로고침 후 재시도 해주세요.");
        }
    }
}