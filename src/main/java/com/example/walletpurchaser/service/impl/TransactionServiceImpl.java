package com.example.walletpurchaser.service.impl;

import com.example.walletpurchaser.exception.ResourceNotFoundException;
import com.example.model.Transaction;
import com.example.model.User;
import com.example.model.Product;
import com.example.model.TransactionStatus;
import com.example.walletpurchaser.Repository.TransactionRepository;
import com.example.walletpurchaser.service.TransactionService;
import com.example.walletpurchaser.service.validation.TransactionStateValidator;
import com.example.walletpurchaser.Repository.UserRepository;
import com.example.walletpurchaser.Repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final TransactionStateValidator transactionStateValidator;


    @Override
    public Transaction createTransaction(Transaction transaction) {
        // You might want to add business validations here
        return transactionRepository.save(transaction);
    }

    @Override
    public Optional<Transaction> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }

    @Override
    public List<Transaction> getTransactionsByUserId(Long userId) {
        return transactionRepository.findByUserId(userId);
    }

    @Override
    public List<Transaction> getTransactionsByStatus(TransactionStatus status) {
        return transactionRepository.findByStatus(status);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    @Transactional
    public Transaction updateTransactionStatus(Long transactionId, TransactionStatus newStatus) {
        Transaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));

        transactionStateValidator.validateTransition(transaction.getStatus(), newStatus);

        transaction.setStatus(newStatus);
        return transactionRepository.save(transaction);
    }


    private void notifyUser(Transaction transaction, String message) {
        System.out.println("🔔 Notify User " + transaction.getUser().getEmail() + ": " + message);
    }
    @Override
    public List<Transaction> filterTransactions(TransactionStatus status, LocalDateTime from, LocalDateTime to) {
        return transactionRepository.findByStatus(status);
    }
    @Override
    public Transaction createTransaction(Long userId, Long productId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        Transaction transaction = Transaction.builder()
                .user(user)
                .product(product)
                .status(TransactionStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .amount(product.getPrice())
                .build();

        return transactionRepository.save(transaction);
    }

}