package com.keyin.service;

import com.keyin.model.Account;
import com.keyin.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JpaUserDetailsServiceUnitTest {

    JpaUserDetailsService jpaUserDetailsServiceUnderTest;

    @Mock
    AccountRepository mockAccountRepository;

    @BeforeEach
    void setup() {
        this.jpaUserDetailsServiceUnderTest = new JpaUserDetailsService(this.mockAccountRepository);
    }

    @Test
    @DisplayName("Should load user with role USER by username")
    void shouldLoadUserByUsername() {
        String username = "test";
        String passwordEncoded = "{bcrypt}" + BCrypt.hashpw("test", BCrypt.gensalt(10));

        when(this.mockAccountRepository.findByName(username))
                .thenReturn(Optional.of(new Account(username, passwordEncoded)));

        UserDetails userDetails = this.jpaUserDetailsServiceUnderTest.loadUserByUsername(username);

        assertAll(
                () -> assertEquals(username, userDetails.getUsername()),
                () -> assertEquals("[ROLE_USER]", userDetails.getAuthorities().toString()),
                () -> assertEquals(passwordEncoded, userDetails.getPassword())
        );
    }

    @Test
    @DisplayName("Should throw UsernameNotFoundException when there is no such user in the system")
    void shouldThrowUsernameNotFoundException() {
        String username = "unknown";

        when(this.mockAccountRepository.findByName(username))
                .thenReturn(Optional.empty());

        UsernameNotFoundException usernameNotFoundException = assertThrows(
                UsernameNotFoundException.class,
                () -> this.jpaUserDetailsServiceUnderTest.loadUserByUsername(username)
        );

        assertEquals("Account with name: \"" + username + "\" not found", usernameNotFoundException.getMessage());
    }
}