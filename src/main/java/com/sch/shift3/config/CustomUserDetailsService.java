package com.sch.shift3.config;

import com.sch.shift3.user.entity.Account;
import com.sch.shift3.user.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("called loadUserByUsername. username: {}", username);
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Account not found."));

        if (account.isBan() && !Objects.equals(account.getRole(), "ROLE_ADMIN")) {
            throw new UsernameNotFoundException("계정이 정지되었습니다. 관리자에게 문의해주세요.");
        }

        return new SecurityUser(account);
    }
}
