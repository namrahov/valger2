package com.unitech.model.exception;

import lombok.Getter;

@Getter
public class CurrencyException extends RuntimeException {
    private final int status;

    public CurrencyException(String message, int status) {
        super(message);
        this.status = status;
    }
}
