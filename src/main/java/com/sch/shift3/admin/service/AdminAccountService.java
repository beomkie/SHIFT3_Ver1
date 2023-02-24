package com.sch.shift3.admin.service;

import com.sch.shift3.user.entity.Account;
import com.sch.shift3.user.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminAccountService {

    private final AccountRepository accountRepository;
    public List<Account> getAllAccounts() {
        Sort sort = Sort.by("id").descending();
        return accountRepository.findAll(sort);
    }

    public Optional<Account> getAccountById(Integer id){
        return accountRepository.findById(id);
    }
}
