package com.sch.shift3.user.service;

import com.sch.shift3.config.OAuth2Attribute;
import com.sch.shift3.config.SecurityUser;
import com.sch.shift3.user.entity.Account;
import com.sch.shift3.user.repository.AccountRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final AccountRepository accountRepository;
    private final HttpSession httpSession;

    @Override
    public SecurityUser loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        //  1번
        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();

        //	2번
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

        //	3번
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        log.info("registrationId = {}", registrationId);
        log.info("userNameAttributeName = {}", userNameAttributeName);

        // 4번
        OAuth2Attribute oAuth2Attribute =
                OAuth2Attribute.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        var memberAttribute = oAuth2Attribute.convertToMap();
        log.info("memberAttribute = {}", memberAttribute);

        Account account = saveOrUpdate(oAuth2Attribute);
        httpSession.setAttribute("account", account);

        // OAuth2User대신 Account를 사용하고 싶다면 아래와 같이 구현하면 된다.
         return new SecurityUser(account, memberAttribute);
//        return new DefaultOAuth2User(
//                Collections.singleton(new SimpleGrantedAuthority(account.getRole())),
//                memberAttribute, "email");
    }

    @Transactional
    Account saveOrUpdate(OAuth2Attribute attributes) {
        Account user = accountRepository.findByUsername(attributes.getEmail())
                .orElse(attributes.toEntity());

        return accountRepository.save(user);
    }
}
