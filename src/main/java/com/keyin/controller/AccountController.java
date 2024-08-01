package com.keyin.controller;

import com.keyin.dto.AccountDTO;
import com.keyin.exception.AccountNameExistsException;
import com.keyin.model.Account;
import com.keyin.service.AccountService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public String getAccount() {
        return "Hello Account Page";
    }

    @GetMapping("/registration")
    public String getAccountRegistration() {
        return "Hello Account Registration Page";
    }

    @PostMapping("/registration")
    public Account postAccountRegistration(@RequestBody AccountDTO accountDTO) throws AccountNameExistsException {
        return this.accountService.createAccount(accountDTO);
    }
}
