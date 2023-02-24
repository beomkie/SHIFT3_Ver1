package com.sch.shift3.config;

import com.sch.shift3.user.repository.AccountRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final AccountRepository accountRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();

        AtomicReference<String> targetUrl = new AtomicReference<>();
        accountRepository.findByUsername(oAuth2User.getAttribute("email"))
                .ifPresentOrElse(
                        account -> {
                            if (account.isFirstLogin()) {
                                targetUrl.set(UriComponentsBuilder.fromUriString("/mypage")
                                        .queryParam("firstLogin", true)
                                        .build().toUriString());
                            } else {
                                // 이전 주소로 리다이렉트 한다
                                String previousUrl = (String) request.getSession().getAttribute("previousUrl");
                                if (previousUrl != null && !previousUrl.isEmpty()) {
                                    targetUrl.set(previousUrl);
                                } else {
                                    targetUrl.set("/");
                                }
                            }
                        },
                        () -> {
                            targetUrl.set(UriComponentsBuilder.fromUriString("/mypage")
                                    .queryParam("firstLogin", true)
                                    .build().toUriString());
                        }
                );

        // targetUrl 조건문 추가
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            targetUrl.set("/admin");
        }

        getRedirectStrategy().sendRedirect(request, response, targetUrl.get());
    }
}
