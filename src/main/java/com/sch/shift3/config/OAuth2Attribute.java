package com.sch.shift3.config;


import com.sch.shift3.user.entity.Account;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@ToString
@Builder
@Getter
public class OAuth2Attribute {
    private Map<String, Object> attributes;
    private String attributeKey;
    private String email;
    private String name;
    private String provider;

    public static OAuth2Attribute of(String provider, String attributeKey,
                                     Map<String, Object> attributes) {
        return switch (provider) {
            case "google" -> ofGoogle(attributeKey, attributes);
            case "kakao" -> ofKakao("email", attributes);
            case "naver" -> ofNaver("id", attributes);
            case "facebook" -> ofFacebook("id", attributes);
            default -> throw new RuntimeException();
        };
    }

    private static OAuth2Attribute ofGoogle(String attributeKey,
                                            Map<String, Object> attributes) {
        return OAuth2Attribute.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .attributes(attributes)
                .attributeKey(attributeKey)
                .provider("google")
                .build();
    }

    private static OAuth2Attribute ofFacebook(String attributeKey,
                                              Map<String, Object> attributes) {
        return OAuth2Attribute.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .attributes(attributes)
                .attributeKey(attributeKey)
                .provider("facebook")
                .build();
    }

    private static OAuth2Attribute ofKakao(String attributeKey,
                                           Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

        return OAuth2Attribute.builder()
                .email((String) kakaoAccount.get("email"))
                .name((String) ((Map<?, ?>) kakaoAccount.get("profile")).get("nickname"))
                .attributes(kakaoAccount)
                .attributeKey(attributeKey)
                .provider("kakao")
                .build();
    }

    private static OAuth2Attribute ofNaver(String attributeKey,
                                           Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuth2Attribute.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .attributes(response)
                .attributeKey(attributeKey)
                .provider("naver")
                .build();
    }

    public Map<String, Object> convertToMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", attributeKey);
        map.put("key", attributeKey);
        map.put("name", name);
        map.put("email", email);

        return map;
    }

    public Account toEntity() {
        return Account.builder()
                .username(email)
                .name(name)
                .provider(provider)
                .build();
    }
}
