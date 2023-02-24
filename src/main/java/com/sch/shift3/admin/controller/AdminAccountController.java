package com.sch.shift3.admin.controller;

import com.sch.shift3.admin.service.AdminAccountService;
import com.sch.shift3.user.dto.AccountDto;
import com.sch.shift3.user.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/account")
@RequiredArgsConstructor
public class AdminAccountController {
    private final AdminAccountService adminAccountService;
    private final AccountRepository accountRepository;

    @Transactional
    @PostMapping("/edit/{accountId}")
    public ResponseEntity<String> editAccount(@PathVariable Integer accountId, AccountDto accountDto){

        adminAccountService.getAccountById(accountId)
                        .ifPresent(
                                account -> {
                                    account.updateInformation(accountDto);
                                    if (accountDto.getRole().contains("ROLE_")){
                                        account.changeRole(accountDto.getRole());
                                    }
                                    accountRepository.save(account);
                                }
                        );
        return ResponseEntity.noContent().build();
    }
}
