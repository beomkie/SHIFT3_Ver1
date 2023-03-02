package com.sch.shift3.user.service;

import com.sch.shift3.user.dto.EmailAuthDto;
import com.sch.shift3.user.entity.Account;
import com.sch.shift3.user.repository.AccountRepository;
import com.sch.shift3.user.repository.EmailAuthLogRepository;
import com.sch.shift3.utill.EmailVerifyUtil;
import com.sch.shift3.utill.MailUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AccountEmailService {
    private final EmailVerifyUtil authSecurityUtil;
    private final EmailAuthLogRepository emailAuthLogRepository;
    private final MailUtil mailing;
    private final AccountRepository accountRepository;

    /**
     * 핸드폰 인증 요청 메서드
     * @param email : 이메일 주소
     * @param request : http 요청 객체
     * @return 인증코드 문자열
     */
    @Transactional
    public String sendAuthCode(String email, HttpServletRequest request, String type) {
        String title = "시프트삼 인증번호 안내 메일입니다.";
        if (Objects.equals(type, "회원가입")){
            title = "시프트삼 회원가입 인증번호 안내 메일입니다.";
        } else if (Objects.equals(type, "비밀번호 찾기")){
            title = "시프트삼 비밀번호 찾기 인증번호 안내 메일입니다.";
        }
        // 일일 전송 횟수 제한
        String clientIP = authSecurityUtil.getClientIP(request);
        if (authSecurityUtil.checkAuthAttemptsExceededByIP(clientIP))
            throw new RuntimeException("인증 시도 횟수 초과");

        LocalDateTime expireAt = LocalDateTime.now().plusMinutes(10);

        // 인증 코드 전송
        String authCode = EmailVerifyUtil.makeAuthCode();

        var emailRequest = new EmailAuthDto.EmailRequest(
                email,
                title,
                authCode);
        mailing.sendMail(emailRequest);

        // 인증 코드 저장
        var saveAuthReq = new EmailAuthDto.SaveAuthReq(
                email,
                authCode,
                expireAt,
                clientIP);

        emailAuthLogRepository.save(saveAuthReq.toEntity());
        return authCode;
    }


    @Transactional
    public Optional<Account> checkEmailInformaton(String email, String realName) {
        return accountRepository.findByUsernameAndName(email, realName);
    }
}
