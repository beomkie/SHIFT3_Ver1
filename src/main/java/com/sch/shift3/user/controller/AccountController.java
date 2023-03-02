package com.sch.shift3.user.controller;

import com.sch.shift3.config.CurrentUser;
import com.sch.shift3.user.dto.AccountDto;
import com.sch.shift3.user.entity.Account;
import com.sch.shift3.user.entity.AccountPasswordChange;
import com.sch.shift3.user.repository.AccountPasswordChangeRepository;
import com.sch.shift3.user.repository.AccountRepository;
import com.sch.shift3.utill.EmailVerifyUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {
    private final AccountPasswordChangeRepository accountPasswordChangeRepository;
    private final AccountRepository accountRepository;
    private final EmailVerifyUtil emailVerifyUtil;

    @Transactional
    @PostMapping("/signup.do")
    public ResponseEntity<String> signupController(@ModelAttribute AccountDto accountDto){
        log.info("accountDto {}", accountDto);
        boolean emailChk = emailVerifyUtil.verify(accountDto.getUsername(), accountDto.getEmailCode());
        if (!emailChk)
            return ResponseEntity.badRequest().body("인증번호가 잘못되었습니다.");

        if (accountRepository.findByUsername(accountDto.getUsername()).isPresent())
            return ResponseEntity.badRequest().body("이미 존재하는 이메일입니다. 다른 이메일로 다시 인증해주세요.");

        accountRepository.save(accountDto.toEntity());
        return ResponseEntity.noContent().build();
    }


    @Transactional
    @PostMapping("/update.do")
    public ResponseEntity<String> editAccountController(@ModelAttribute AccountDto accountDto, @CurrentUser Account account){
        log.info("accountDto {}", accountDto);

        if (account == null)
            return ResponseEntity.badRequest().body("로그인 후 이용가능합니다.");

        if (accountDto.getPassword() != null) {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            accountDto.setPassword(bCryptPasswordEncoder.encode(accountDto.getPassword()));
        }
        account.updateInformation(accountDto);
        accountRepository.save(account);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    @GetMapping("/forgot/verify.do")
    public ResponseEntity<String> forgotVerifyController(@RequestParam String username, @RequestParam String verifyCode){
        boolean emailChk = emailVerifyUtil.verify(username, verifyCode);
        if (!emailChk)
            return ResponseEntity.badRequest().body("인증번호가 잘못되었습니다.");

        accountPasswordChangeRepository.findByUsername(username).ifPresent(accountPasswordChangeRepository::delete);
        AccountPasswordChange accountPasswordChange = new AccountPasswordChange(username);
        accountPasswordChangeRepository.save(accountPasswordChange);

        // remove auth log
//        emailVerifyUtil.removeAuthLog(username);

        return ResponseEntity.ok().body(accountPasswordChange.getHash());
    }

    @Transactional
    @GetMapping("/forgot/change.do")
    public ResponseEntity<String> passwordForgotChangeController(String hash, String password){
        if (hash == null || password == null)
            return ResponseEntity.badRequest().body("잘못된 접근입니다.");

        AccountPasswordChange accountPasswordChange = accountPasswordChangeRepository.findByHash(hash).orElseThrow(() -> new IllegalArgumentException("잘못된 접근입니다."));
        if (accountPasswordChange.getExpiredAt().isBefore(LocalDateTime.now()))
            return ResponseEntity.badRequest().body("만료된 링크입니다.");

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        Account account = accountRepository.findByUsername(accountPasswordChange.getUsername()).orElseThrow(() -> new IllegalArgumentException("잘못된 접근입니다."));
        account.changePassword(bCryptPasswordEncoder.encode(password));
        accountRepository.save(account);
        accountPasswordChangeRepository.delete(accountPasswordChange);
        return ResponseEntity.noContent().build();
    }

}
