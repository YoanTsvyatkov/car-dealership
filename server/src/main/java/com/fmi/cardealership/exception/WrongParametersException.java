package com.fmi.cardealership.exception;

public class WrongParametersException extends RuntimeException {
    public WrongParametersException() {
    }

    public WrongParametersException(String message) {
        super(message);
    }
}
