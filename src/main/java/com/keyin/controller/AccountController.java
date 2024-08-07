package com.keyin.controller;

import com.keyin.dto.AccountDTO;
import com.keyin.dto.ResponseDTO;
import com.keyin.exception.AccountNameExistsException;
import com.keyin.model.Account;
import com.keyin.service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

    @Autowired
    public AccountController(
            AccountService accountService,
            AuthenticationManager authenticationManager
    ) {
        this.accountService = accountService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping
    public ResponseEntity<Account> getAccount(Principal principal) {
        Optional<Account> accountOptional = this.accountService.findAccountByName(principal.getName());

        return ResponseEntity.of(accountOptional);
    }

    @GetMapping("/registration")
    public String getAccountRegistration() {
        return "Hello Account Registration Page";
    }

    @PostMapping("/registration")
    public ResponseEntity<?> postAccountRegistration(@RequestBody AccountDTO accountDTO) {
        try {
            Account account = this.accountService.createAccount(accountDTO);

            return new ResponseEntity<>(account, HttpStatus.CREATED);
        } catch (AccountNameExistsException e) {
            return new ResponseEntity<>(new ResponseDTO(accountDTO.name(), e.getMessage()), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/login")
    public String getAccountLogin() {
        return "Hello Account Login Page";
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> postAccountLogin(
            @RequestBody AccountDTO accountDTO,
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse
    ) {
        UsernamePasswordAuthenticationToken token =
                UsernamePasswordAuthenticationToken.unauthenticated(accountDTO.name(), accountDTO.password());

        Authentication authentication = this.authenticationManager.authenticate(token);

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);

        this.securityContextRepository.saveContext(context, httpServletRequest, httpServletResponse);

        return ResponseEntity.ok(
                new ResponseDTO(accountDTO.name(), "Logged in successfully")
        );
    }
}
