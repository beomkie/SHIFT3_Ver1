package com.sch.shift3.user.controller;

import com.sch.shift3.user.service.AccountEmailService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class EmailAuthController {
    private final AccountEmailService accountEmailService;

    @GetMapping("/auth/email/send")
    public ResponseEntity<String> sendAuth(String email, HttpServletRequest request) {

        accountEmailService.sendAuthCode(email, request);
        return ResponseEntity.noContent().build();
    }
}
