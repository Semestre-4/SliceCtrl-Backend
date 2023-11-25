package com.mensal.slicectrl.exception;

import javax.naming.AuthenticationException;

public class LogInException extends AuthenticationException {
    public LogInException(String message) {
        super(message);
    }
}
