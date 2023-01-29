package com.sch.shift3.config;

import com.sch.shift3.user.entity.Account;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class SecurityUser extends User {
    private Account account;

    public SecurityUser(Account account) {
        super(account.getUsername(), account.getPassword(), AuthorityUtils.createAuthorityList(account.getRole().toString()));
        this.account = account;
    }
}
