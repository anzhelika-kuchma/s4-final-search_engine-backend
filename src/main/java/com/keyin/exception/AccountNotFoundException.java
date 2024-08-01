package com.keyin.exception;

public class AccountNotFoundException extends Throwable {
    public AccountNotFoundException(String message) {
        super(message);
    }
}
