package com.sch.shift3.user.controller;

import com.sch.shift3.config.CurrentUser;
import com.sch.shift3.user.dto.AccountDto;
import com.sch.shift3.user.entity.Account;
import com.sch.shift3.user.repository.AccountRepository;
import com.sch.shift3.utill.EmailVerifyUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {
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

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        accountDto.setPassword(bCryptPasswordEncoder.encode(accountDto.getPassword()));
        account.updateInformation(accountDto);
        accountRepository.save(account);
        return ResponseEntity.noContent().build();

    }


}
