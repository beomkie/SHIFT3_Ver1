package com.sch.shift3.user.controller;

import com.sch.shift3.user.entity.Account;
import com.sch.shift3.user.service.AccountEmailService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class EmailAuthController {
    private final AccountEmailService accountEmailService;

    @GetMapping("/auth/email/send.do")
    public ResponseEntity<String> sendAuth(String email, String realName, HttpServletRequest request) {
        if (realName == null){
            // 회원가입 인증 코드
            accountEmailService.sendAuthCode(email, request, "회원가입");
        } else {
            // 비밀번호 찾기 인증 코드
            Optional<Account> account = accountEmailService.checkEmailInformaton(email, realName);
            if (account.isEmpty())
                return ResponseEntity.badRequest().body("일치하는 회원 정보가 없습니다. 이름과 이메일을 확인해주세요.");

            if (Boolean.TRUE.equals(account.get().getBan()))
                return ResponseEntity.badRequest().body("정지된 회원입니다. 관리자에게 문의해주세요.");

            if (account.get().isSocialLogin())
                return ResponseEntity.badRequest().body("소셜 로그인 회원입니다. 소셜 로그인을 통해 비밀번호를 변경해주세요.");

            accountEmailService.sendAuthCode(email, request, "비밀번호 찾기");
        }
        return ResponseEntity.noContent().build();
    }
}
