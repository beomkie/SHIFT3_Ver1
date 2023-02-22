package com.sch.shift3.config;

import com.sch.shift3.user.entity.Account;
import com.sch.shift3.user.repository.AccountRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final AccountRepository accountRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();

        // 최초 로그인이라면 회원가입 처리를 한다.
        String targetUrl;
        Account account = accountRepository.findByUsername(oAuth2User.getAttribute("name"))
                .orElseGet(Account::new);
        if (account.isFirstLogin()) {
            targetUrl = UriComponentsBuilder.fromUriString("/mypage")
                    .queryParam("firstLogin", true)
                    .build().toUriString();
        } else {
            targetUrl = "/";
        }

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
