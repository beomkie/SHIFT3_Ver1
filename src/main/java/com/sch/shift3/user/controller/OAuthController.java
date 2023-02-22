//package com.sch.shift3.user.controller;
//
//import com.sch.shift3.user.dto.OAuthTokenDto;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import net.minidev.json.JSONObject;
//import net.minidev.json.parser.JSONParser;
//import net.minidev.json.parser.ParseException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.Map;
//import java.util.Objects;
//
//@Slf4j
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/oauth2/login")
//public class OAuthController {
//    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
//    private String kakaoClientId;
//
//    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
//    private String kakaoRedirectUri;
//
//    @Autowired
//    private final RestTemplate restTemplate;
//
//
////
////    @ResponseBody
//    @GetMapping("")
//    public void login(@RequestParam Map<String,Object> paramMap) {
//        String type = (String) paramMap.get("type");
//
//        switch (type) {
//            case "kakao" -> {
//
//                /* get Access Token with restTemplate
//                POST /oauth/token HTTP/1.1
//                Host: kauth.kakao.com
//                Content-type: application/x-www-form-urlencoded;charset=utf-8*/
//
//                log.info("kakaoClientId = {} ", kakaoClientId);
//                log.info("kakaoRedirectUri = {}", kakaoRedirectUri);
//
//                String code = (String) paramMap.get("code");
//                log.info("code = {}", code);
//                String url = "https://kauth.kakao.com/oauth/token";
//                MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
//                body.add("grant_type", "authorization_code");
//                body.add("client_id", kakaoClientId);
//                body.add("redirect_uri", kakaoRedirectUri);
//                body.add("code", code);
//                OAuthTokenDto.kakao response = restTemplate.postForObject(url, body, OAuthTokenDto.kakao.class);
//                log.info("response = {}", response);
//                // base64 decode response.getId_token()
//                // get email from decoded response.getId_token()
//                // todo validate response
//                if (Objects.requireNonNull(response).getId_token() == null) {
//                    log.info("response is null");
//                } else {
//                    String idToken = response.getId_token();
//                    idToken = idToken.split("\\.")[1];
//                    log.info("idToken = {}", idToken);
//                    // base64 decode idToken
//                    String decodedIdToken = new String(java.util.Base64.getDecoder().decode(idToken));
//                    // String to Json
//                    JSONParser parser = new JSONParser();
//                    try {
//                        JSONObject jsonObject = (JSONObject) parser.parse(decodedIdToken);
//                        log.info("jsonObject = {}", jsonObject);
//                        String email = (String) jsonObject.get("email");
//
//                        // pass OAuth email to userService
//
//
//                    } catch (ParseException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//
//            }
//            case "facebook" -> log.info("facebook");
//            case "naver" -> log.info("naver");
//            default -> log.info("default");
//        }
//    }
//}
