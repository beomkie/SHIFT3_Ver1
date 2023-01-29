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

        return new SecurityUser(account);
    }
}
