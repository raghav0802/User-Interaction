package com.example.walletpurchaser.service;
import com.example.model.Wallet;

import java.math.BigDecimal;
import java.util.Optional;

public interface WalletService {

    Wallet createWalletForUser(Long userId);

    Optional<Wallet> findByUserId(Long userId);

    BigDecimal getBalance(Long userId);

    Wallet addFunds(Long userId, BigDecimal amount);

    Wallet deductFunds(Long userId, BigDecimal amount);

    Wallet refundFunds(Long userId, BigDecimal amount);

    boolean hasSufficientBalance(Long userId, BigDecimal amount);
}

