package com.keyin.service;

import com.keyin.dto.AccountDTO;
import com.keyin.exception.AccountNameExistsException;
import com.keyin.model.Account;
import com.keyin.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceUnitTest {
    AccountService accountServiceUnderTest;

    @Mock
    AccountRepository mockAccountRepository;

    @Mock
    PasswordEncoder mockPasswordEncoder;

    @Captor
    ArgumentCaptor<Account> argumentCaptor;

    @BeforeEach
    void setup() {
        this.accountServiceUnderTest = new AccountService(
                this.mockAccountRepository,
                this.mockPasswordEncoder
        );
    }

    @Test
    @DisplayName("Should find account with name 'test'")
    void shouldFindAccountWithNameTest() {
        String name = "test";
        String passwordEncoded = "{bcrypt}" + BCrypt.hashpw("test", BCrypt.gensalt(10));

        Account accountExpected = new Account(name, passwordEncoded);

        when(this.mockAccountRepository.findByName(name))
                .thenReturn(Optional.of(accountExpected));

        Account accountActual = this.accountServiceUnderTest
                .findAccountByName(name)
                .orElse(null);

        assertNotNull(accountActual);
        assertAll(
                () -> assertEquals(name, accountActual.getName()),
                () -> assertEquals(passwordEncoded, accountActual.getPassword())
        );
    }

    @Test
    @DisplayName("Should NOT find account with name 'test'")
    void shouldNotFindAccountWithNameTest() {
        String name = "test";

        when(this.mockAccountRepository.findByName(name))
                .thenReturn(Optional.empty());

        Account accountActual = this.accountServiceUnderTest
                .findAccountByName(name)
                .orElse(null);

        assertNull(accountActual);
    }

    @Test
    @DisplayName("Should return TRUE when account with the name 'test' already exist")
    void shouldReturnTrueWhenAccountWithNameTestExist() {
        String name = "test";
        String passwordEncoded = "{bcrypt}" + BCrypt.hashpw("test", BCrypt.gensalt(10));

        Account accountExpected = new Account(name, passwordEncoded);

        when(this.mockAccountRepository.findByName(name))
                .thenReturn(Optional.of(accountExpected));

        assertTrue(this.accountServiceUnderTest.checkDuplicateAccountByName(name));
    }

    @Test
    @DisplayName("Should return FALSE if account with the name 'test' does not exist")
    void shouldReturnFalseIfAccountWithNameTestDoesNotExist() {
        String name = "test";

        when(this.mockAccountRepository.findByName(name))
                .thenReturn(Optional.empty());

        assertFalse(this.accountServiceUnderTest.checkDuplicateAccountByName(name));
    }

    @Test
    @DisplayName("Should create an account")
    void shouldCreateAccount() throws AccountNameExistsException {
        String name = "test";
        String password = "test";
        String passwordEncoded = "{bcrypt}" + BCrypt.hashpw(password, BCrypt.gensalt(10));

        when(this.mockAccountRepository.findByName(name))
                .thenReturn(Optional.empty());

        when(this.mockPasswordEncoder.encode(password))
                .thenReturn(passwordEncoded);

        when(this.mockAccountRepository.save(any(Account.class)))
                .thenReturn(new Account(name, passwordEncoded));

        Account accountActual =
                this.accountServiceUnderTest.createAccount(new AccountDTO(name, password));

        verify(this.mockAccountRepository, times(1)).findByName(name);
        verify(this.mockPasswordEncoder, times(1)).encode(password);
        verify(this.mockAccountRepository).save(this.argumentCaptor.capture());

        assertAll(
                () -> assertEquals(name, this.argumentCaptor.getValue().getName()),
                () -> assertEquals(passwordEncoded, this.argumentCaptor.getValue().getPassword())
        );
    }

    @Test
    @DisplayName("Should throw AccountNameExistsException")
    void shouldThrowAccountNameExistsException() throws AccountNameExistsException {
        String name = "test";
        String password = "test";
        String passwordEncoded = "{bcrypt}" + BCrypt.hashpw(password, BCrypt.gensalt(10));

        Account accountExpected = new Account(name, passwordEncoded);

        when(this.mockAccountRepository.findByName(name))
                .thenReturn(Optional.of(accountExpected));

        AccountNameExistsException accountNameExistsException = assertThrows(
                AccountNameExistsException.class,
                () -> this.accountServiceUnderTest.createAccount(new AccountDTO(name, password))
        );

        assertEquals(
                "Account already exist. Try another name",
                accountNameExistsException.getMessage()
        );

        verify(this.mockPasswordEncoder, times(0)).encode(password);
        verify(this.mockAccountRepository, times(0)).save(any(Account.class));
    }
}