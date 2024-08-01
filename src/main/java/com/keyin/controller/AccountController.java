package com.keyin.controller;
import com.keyin.dto.AccountDTO;
import com.keyin.exception.AccountNameExistsException;
import com.keyin.model.Account;
import com.keyin.service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
    @GetMapping("/login")
    public String getAccountLogin() {
        return "Hello Account Login Page";
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> postAccountLogin(
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

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("message", "Logged in successfully");

        return ResponseEntity.ok(responseBody);
    }
}
