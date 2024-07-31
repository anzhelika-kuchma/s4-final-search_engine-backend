package com.keyin.service;

import com.keyin.model.Account;
import com.keyin.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Optional<Account> findAccountById(Long id) {
        return this.accountRepository.findById(id);
    }
}
