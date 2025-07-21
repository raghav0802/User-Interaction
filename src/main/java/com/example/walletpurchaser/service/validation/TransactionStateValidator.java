package com.example.walletpurchaser.service.validation;

import com.example.walletpurchaser.exception.TransactionStateException;
import com.example.model.TransactionStatus;
import org.springframework.stereotype.Service;

@Service
public class TransactionStateValidator {

    public void validateTransition(TransactionStatus current, TransactionStatus next) {
        if (current == TransactionStatus.SUCCESS) {
            throw new TransactionStateException("Cannot change status of a successful transaction.");
        }

        if (current == TransactionStatus.FAILED && next != TransactionStatus.PENDING) {
            throw new TransactionStateException("Can only retry failed transaction to pending.");
        }

        if (current == TransactionStatus.PENDING &&
                (next == TransactionStatus.SUCCESS || next == TransactionStatus.FAILED)) {
            return;
        }

        if (current != TransactionStatus.PENDING) {
            throw new TransactionStateException("Invalid status transition from " + current + " to " + next);
        }
    }
}
