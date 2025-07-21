package com.example.walletpurchaser.service.impl;

import com.example.walletpurchaser.exception.InsufficientBalanceException;
import com.example.walletpurchaser.exception.ResourceNotFoundException;
import com.example.model.User;
import com.example.model.Wallet;
import com.example.walletpurchaser.Repository.UserRepository;
import com.example.walletpurchaser.Repository.WalletRepository;
import com.example.walletpurchaser.service.WalletService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final UserRepository userRepository;

    @Override
    public Wallet createWalletForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Prevent duplicate wallets
        walletRepository.findByUserId(userId).ifPresent(w -> {
            throw new IllegalStateException("Wallet already exists for user");
        });

        Wallet wallet = Wallet.builder()
                .user(user)
                .balance(BigDecimal.ZERO)
                .build();

        return walletRepository.save(wallet);
    }

    @Override
    public Optional<Wallet> findByUserId(Long userId) {
        return walletRepository.findByUserId(userId);
    }

    @Override
    public BigDecimal getBalance(Long userId) {
        return findWallet(userId).getBalance();
    }

    @Override
    @Transactional
    public Wallet addFunds(Long userId, BigDecimal amount) {
        if (amount.signum() <= 0) throw new IllegalArgumentException("Amount must be positive");

        Wallet wallet = findWallet(userId);
        wallet.setBalance(wallet.getBalance().add(amount));
        return walletRepository.save(wallet);
    }

    @Override
    @Transactional
    public Wallet deductFunds(Long userId, BigDecimal amount) {
        if (amount.signum() <= 0) throw new IllegalArgumentException("Amount must be positive");

        Wallet wallet = findWallet(userId);

        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException("Not enough balance");
        }

        wallet.setBalance(wallet.getBalance().subtract(amount));
        return walletRepository.save(wallet);
    }

    @Override
    @Transactional
    public Wallet refundFunds(Long userId, BigDecimal amount) {
        return addFunds(userId, amount);
    }

    @Override
    public boolean hasSufficientBalance(Long userId, BigDecimal amount) {
        Wallet wallet = findWallet(userId);
        return wallet.getBalance().compareTo(amount) >= 0;
    }

    private Wallet findWallet(Long userId) {
        return walletRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Wallet not found"));
    }
}
