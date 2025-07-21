package com.example.walletpurchaser.controller;

import com.example.model.Transaction;
import com.example.model.TransactionStatus;
import com.example.walletpurchaser.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    // 🔹 Create a new transaction (e.g., for product purchase)
    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestParam Long userId,
                                                         @RequestParam Long productId) {
        Transaction tx = transactionService.createTransaction(userId, productId);
        return ResponseEntity.ok(tx);
    }

    // 🔹 Get transaction by ID
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
        return transactionService.getTransactionById(id)
                .map(transaction -> ResponseEntity.ok(transaction)) // if present, wrap in 200 OK
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    // 🔹 Get all transactions for a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Transaction>> getTransactionsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(transactionService.getTransactionsByUserId(userId));
    }

    // 🔹 Optional: Filter transactions by status or date
    @GetMapping
    public ResponseEntity<List<Transaction>> filterTransactions(
            @RequestParam(required = false) TransactionStatus status,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to
    ) {
        return ResponseEntity.ok(transactionService.filterTransactions(status, from, to));
    }
}
