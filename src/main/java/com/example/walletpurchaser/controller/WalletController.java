package com.example.walletpurchaser.controller;



import com.example.model.Wallet;
import com.example.walletpurchaser.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    // 🔹 Create wallet for user
    @PostMapping("/{userId}")
    public ResponseEntity<Wallet> createWallet(@PathVariable Long userId) {
        Wallet wallet = walletService.createWalletForUser(userId);
        return ResponseEntity.ok(wallet);
    }

    // 🔹 Get wallet balance
    @GetMapping("/{userId}/balance")
    public ResponseEntity<BigDecimal> getBalance(@PathVariable Long userId) {
        BigDecimal balance = walletService.getBalance(userId);
        return ResponseEntity.ok(balance);
    }

    // 🔹 Add funds
    @PostMapping("/{userId}/add")
    public ResponseEntity<Wallet> addFunds(@PathVariable Long userId, @RequestParam BigDecimal amount) {
        Wallet updatedWallet = walletService.addFunds(userId, amount);
        return ResponseEntity.ok(updatedWallet);
    }

    // 🔹 Deduct funds
    @PostMapping("/{userId}/deduct")
    public ResponseEntity<Wallet> deductFunds(@PathVariable Long userId, @RequestParam BigDecimal amount) {
        Wallet updatedWallet = walletService.deductFunds(userId, amount);
        return ResponseEntity.ok(updatedWallet);
    }

    // 🔹 Refund funds
    @PostMapping("/{userId}/refund")
    public ResponseEntity<Wallet> refundFunds(@PathVariable Long userId, @RequestParam BigDecimal amount) {
        Wallet updatedWallet = walletService.refundFunds(userId, amount);
        return ResponseEntity.ok(updatedWallet);
    }

    // 🔹 Check sufficient balance
    @GetMapping("/{userId}/check-balance")
    public ResponseEntity<Boolean> hasSufficientBalance(@PathVariable Long userId, @RequestParam BigDecimal amount) {
        boolean result = walletService.hasSufficientBalance(userId, amount);
        return ResponseEntity.ok(result);
    }
}
