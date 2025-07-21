package com.example.walletpurchaser.service;

import com.example.model.Transaction;
import com.example.model.TransactionStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TransactionService {

    Transaction createTransaction(Transaction transaction);

    Optional<Transaction> getTransactionById(Long id);

    List<Transaction> getTransactionsByUserId(Long userId);

    List<Transaction> getTransactionsByStatus(TransactionStatus status);

    List<Transaction> getAllTransactions();

    Transaction updateTransactionStatus(Long transactionId, TransactionStatus newStatus);

    Transaction createTransaction(Long userId, Long productId);

    List<Transaction> filterTransactions(TransactionStatus status, LocalDateTime from, LocalDateTime to);
}
