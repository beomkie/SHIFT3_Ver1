package com.sch.shift3.user.dto;

import com.sch.shift3.user.entity.AccountEmailAuthLog;
import groovy.transform.builder.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class EmailAuthDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmailRequest {
        private String to;
        private String title;
        private String text;
    }

    @AllArgsConstructor
    public static class SaveAuthReq {
        private String email;
        private String code;
        private LocalDateTime expireAt;
        private String ip;

        public AccountEmailAuthLog toEntity(){
            return AccountEmailAuthLog.builder()
                    .email(email)
                    .code(code)
                    .expireAt(expireAt)
                    .ip(ip)
                    .build();
        }
    }
}
