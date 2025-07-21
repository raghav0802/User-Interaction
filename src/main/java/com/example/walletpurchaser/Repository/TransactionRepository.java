package com.example.walletpurchaser.Repository;


import com.example.model.Transaction;
import com.example.model.TransactionType;
import com.example.model.TransactionStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    //  Get all transactions for a user
    List<Transaction> findByUserId(Long userId);

    //  Filter by type and status
    List<Transaction> findByUserIdAndTypeAndStatus(Long userId, TransactionType type, TransactionStatus status);

    //  Get all transactions in a date range
    List<Transaction> findByUserIdAndCreatedAtBetween(Long userId, LocalDateTime start, LocalDateTime end);

    //  Most recent N transactions
    List<Transaction> findTop10ByUserIdOrderByCreatedAtDesc(Long userId);

    //  Total amount for successful transactions
    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.user.id = :userId AND t.status = 'SUCCESS'")
    Double getTotalSuccessfulAmountSpentByUser(Long userId);

    // Count by status
    long countByStatus(TransactionStatus status);

    //  Find all failed transactions
    List<Transaction> findByStatus(TransactionStatus status);

    //  Find all pending transactions for processing
    List<Transaction> findByStatusAndCreatedAtBefore(TransactionStatus status, LocalDateTime beforeTime);

    //  Optional: transactions for user that are failed or pending
    List<Transaction> findByUserIdAndStatusIn(Long userId, List<TransactionStatus> statuses);


}