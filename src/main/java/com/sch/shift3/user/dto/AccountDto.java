package com.sch.shift3.user.dto;

import com.sch.shift3.user.entity.Account;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
public class AccountDto {
    private Integer id;
    private String name;
    private String username;
    private String nowPassword;
    private String password;
    private String emailCode;
    private String interest;
    private String phoneNumber;
    private Boolean ban;
    private String role;
    private Boolean informationToThirdParties;

    public Account toEntity(){
        if (informationToThirdParties == null)
            informationToThirdParties = false;

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        return Account.builder()
                .name(name)
                .username(username)
                .password(bCryptPasswordEncoder.encode(password))
                .informationToThirdParties(informationToThirdParties)
                .phoneNumber(phoneNumber)
                .interest(interest)
                .provider(null)
                .build();
    }
}
