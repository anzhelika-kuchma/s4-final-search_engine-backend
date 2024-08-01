package com.keyin.exception;

public class AccountNameExistsException extends Throwable {
    public AccountNameExistsException(String message) {
        super(message);
    }
}
