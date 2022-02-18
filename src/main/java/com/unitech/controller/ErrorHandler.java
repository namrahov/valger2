package com.unitech.controller;

import com.unitech.logger.DPLogger;
import com.unitech.model.exception.AccountNotFoundException;
import com.unitech.model.exception.CurrencyException;
import com.unitech.model.exception.ExceptionResponse;
import com.unitech.model.exception.NotEnoughBalanceException;
import com.unitech.model.exception.UserNotFountException;
import com.unitech.model.exception.UserRegisterException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {
    private static final DPLogger log = DPLogger.getLogger(ErrorHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handle(Exception ex) {
        log.error("Exception ", ex);
        return new ExceptionResponse("UNEXPECTED_EXCEPTION");
    }

    @ExceptionHandler(UserRegisterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handle(UserRegisterException ex) {
        log.error("UserRegisterException ", ex);
        return new ExceptionResponse(ex.getMessage());
    }

    @ExceptionHandler(NotEnoughBalanceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handle(NotEnoughBalanceException ex) {
        log.error("NotEnoughBalanceException ", ex);
        return new ExceptionResponse(ex.getMessage());
    }

    @ExceptionHandler(UserNotFountException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse handle(UserNotFountException ex) {
        log.error("UserNotFountException ", ex);
        return new ExceptionResponse(ex.getMessage());
    }

    @ExceptionHandler(AccountNotFoundException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse handle(AccountNotFoundException ex) {
        log.error("AccountNotFoundException ", ex);
        return new ExceptionResponse(ex.getMessage());
    }

    @ExceptionHandler(CurrencyException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse handle(CurrencyException ex) {
        log.error("CurrencyException ", ex);
        return new ExceptionResponse(ex.getMessage());
    }
}
