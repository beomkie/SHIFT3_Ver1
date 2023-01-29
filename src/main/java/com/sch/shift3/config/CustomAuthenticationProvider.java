package com.sch.shift3.config;

import com.sch.shift3.user.entity.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;

@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private CustomUserDetailsService customUserDetails;

    @Autowired
    private PasswordEncoder passwordEncoder;


    // 검쯩을 위한 구현
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("called authenticate");

        String username = authentication.getName();
        String password = (String)authentication.getCredentials();

        Account account = (Account) customUserDetails.loadUserByUsername(username);

        if(!passwordEncoder.matches(password, account.getPassword())){
            throw new BadCredentialsException("BadCredentialsException");
        }

        // 인증에 성공한 경우, 인증된 사용자 정보를 담은 Authentication 객체를 반환
        Collection<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
        roles.add(() -> account.getRole());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(account, null, roles);
        authenticationToken.setDetails(account);

        return authenticationToken;
    }

    // 토큰 타입과 일치할 때 인증
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
