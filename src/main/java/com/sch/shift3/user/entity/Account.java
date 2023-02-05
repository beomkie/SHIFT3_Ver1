package com.sch.shift3.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "account")
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "phone_number", nullable = false, length = 15)
    private String phoneNumber;

    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Column(name = "password", nullable = false, length = 80)
    private String password;

    @Column(name = "role", length = 15)
    private String role;

    @Column(name = "ban")
    private Boolean ban;

    @Column(name = "information_to_third_parties", nullable = false)
    private Boolean informationToThirdParties = false;

    @Column(name = "name", nullable = false, length = 15)
    private String name;

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

}