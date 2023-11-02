package com.management.system.exception;

public class AccountLockedException extends Throwable{
    public AccountLockedException(String message) {
        super(message);
    }
}
