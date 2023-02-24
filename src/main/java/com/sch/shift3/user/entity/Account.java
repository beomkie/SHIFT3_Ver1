package com.sch.shift3.user.entity;

import com.sch.shift3.user.dto.AccountDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "account")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Column(name = "password", length = 80)
    private String password;

    @Builder.Default
    @Column(name = "role", length = 15)
    private String role = "ROLE_USER";

    @Column(name = "ban")
    @Builder.Default
    private Boolean ban = false;

    @Builder.Default
    @Column(name = "information_to_third_parties")
    private Boolean informationToThirdParties = false;

    @Column(name = "name", length = 15)
    private String name;

    @Column(name = "provider", length = 15)
    private String provider;

    @Column(name = "interest")
    private String interest;

    public Boolean getInformationToThirdParties() {
        return informationToThirdParties;
    }

    public void setInformationToThirdParties(Boolean informationToThirdParties) {
        this.informationToThirdParties = informationToThirdParties;
    }

    public boolean isFirstLogin() {
        return this.name == null || this.phoneNumber == null;
    }

    public void updateInformation(AccountDto accountDto){
        if (accountDto.getBan() != null){
            this.ban = accountDto.getBan();
        }

        if (accountDto.getPassword() != null){
            this.password = accountDto.getPassword();
        }

        if (accountDto.getName() != null){
            this.name = accountDto.getName();
        }

        if (accountDto.getInterest() != null){
            this.interest = accountDto.getInterest();
        }

        if (accountDto.getPhoneNumber() != null){
            this.phoneNumber = accountDto.getPhoneNumber();
        }

        if (accountDto.getInformationToThirdParties() != null){
            this.informationToThirdParties = accountDto.getInformationToThirdParties();
        }
    }

    public void changeRole(String role){
        this.role = role;
    }
}