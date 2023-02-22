package com.sch.shift3.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getBan() {
        return ban;
    }

    public void setBan(Boolean ban) {
        this.ban = ban;
    }

    public Boolean getInformationToThirdParties() {
        return informationToThirdParties;
    }

    public void setInformationToThirdParties(Boolean informationToThirdParties) {
        this.informationToThirdParties = informationToThirdParties;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFirstLogin() {
        return this.name == null || this.phoneNumber == null;
    }
}