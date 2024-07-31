package com.keyin.service;

import com.keyin.model.Account;
import com.keyin.model.AccountKeyword;
import com.keyin.model.Keyword;
import com.keyin.repository.AccountKeywordRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AccountKeywordService {
    private final AccountKeywordRepository accountKeywordRepository;

    public AccountKeywordService(AccountKeywordRepository accountKeywordRepository) {
        this.accountKeywordRepository = accountKeywordRepository;
    }

    public AccountKeyword createAccountKeywordAssociation(Account account, Keyword keyword) {
        return this.accountKeywordRepository.save(new AccountKeyword(account, keyword, LocalDateTime.now()));
    }
}
