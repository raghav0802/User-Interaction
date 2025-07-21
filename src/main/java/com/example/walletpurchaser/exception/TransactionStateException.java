package com.example.walletpurchaser.exception;

public class TransactionStateException extends RuntimeException {
    public TransactionStateException(String message) {
        super(message);
    }
}