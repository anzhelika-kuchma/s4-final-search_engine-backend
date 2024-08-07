package com.keyin.service;

import com.keyin.model.Account;
import com.keyin.model.AccountKeyword;
import com.keyin.model.Keyword;
import com.keyin.repository.AccountKeywordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCrypt;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountKeywordServiceUnitTest {
    AccountKeywordService accountKeywordServiceUnderTest;

    @Mock
    AccountKeywordRepository mockAccountKeywordRepository;

    @Captor
    ArgumentCaptor<AccountKeyword> argumentCaptor;

    @BeforeEach
    void setup() {
        this.accountKeywordServiceUnderTest =
                new AccountKeywordService(this.mockAccountKeywordRepository);
    }

    @Test
    @DisplayName("Should create account/keyword association")
    void shouldCreateAccountKeywordAssociation() {
        String name = "test";
        String password = "test";
        String passwordEncoded = "{bcrypt}" + BCrypt.hashpw(password, BCrypt.gensalt(10));

        Account accountExpected = new Account(name, passwordEncoded);
        Keyword keywordExpected = new Keyword(name);

        when(this.mockAccountKeywordRepository.save(any(AccountKeyword.class)))
                .thenReturn(new AccountKeyword(accountExpected, keywordExpected));

        AccountKeyword accountKeywordActual = this.accountKeywordServiceUnderTest
                .createAccountKeywordAssociation(accountExpected, keywordExpected);

        verify(this.mockAccountKeywordRepository).save(this.argumentCaptor.capture());

        assertEquals(accountExpected, this.argumentCaptor.getValue().getAccount());
        assertEquals(keywordExpected, this.argumentCaptor.getValue().getKeyword());
    }
}