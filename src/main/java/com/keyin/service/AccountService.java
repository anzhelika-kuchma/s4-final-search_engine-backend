package com.keyin.service;

import com.keyin.dto.AccountDTO;
import com.keyin.exception.AccountNameExistsException;
import com.keyin.model.Account;
import com.keyin.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<Account> findAccountByName(String name) {
        return this.accountRepository.findByName(name);
    }

    public boolean checkDuplicateAccountByName(String name) {
        return this.findAccountByName(name).isPresent();
    }

    public Account createAccount(AccountDTO accountDTO) throws AccountNameExistsException {
        if (this.checkDuplicateAccountByName(accountDTO.name()))
            throw new AccountNameExistsException("Account already exist. Try another name");

        return this.accountRepository.save(
                new Account(
                        accountDTO.name(),
                        this.passwordEncoder.encode(accountDTO.password())
                )
        );
    }
}
