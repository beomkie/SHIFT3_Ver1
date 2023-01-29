package com.sch.shift3.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@RequiredArgsConstructor
@ConditionalOnDefaultWebSecurity
@Configuration(proxyBeanMethods = false)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetails;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .headers().frameOptions().disable()
            .and()
            .authorizeHttpRequests()
            .requestMatchers("/admin/**").hasRole(SecurityRole.ADMIN.name())
            .requestMatchers("/**").permitAll()
//            .requestMatchers("/api/v1/**").hasRole(SecurityRole.USER.name())
            .anyRequest().authenticated()

            .and()
            .exceptionHandling().accessDeniedPage("/access-denied")
            .and()
            .formLogin()
            .loginPage("/login")
            .loginProcessingUrl("/login.do")
            .usernameParameter("username")
            .passwordParameter("password")
            .defaultSuccessUrl("/")
            .failureUrl("/login?error=true")
            .permitAll()

            .and()
            .logout()
            .logoutSuccessUrl("/")
            .invalidateHttpSession(true);
//            .and()
//            .oauth2Login()
//            .userInfoEndpoint();
//            .userService(customOAuth2UserService);

        return http.build();
    }

}
