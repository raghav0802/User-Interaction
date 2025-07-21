package com.example.walletpurchaser.Repository;

import com.example.model.Wallet;
import com.example.model.User;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    //  Find by owning user
    Optional<Wallet> findByUser(User user);

    Optional<Wallet> findByUserId(Long userId);

    //  Fetch wallet with user eagerly (for dashboard or admin display)
    @Query("SELECT w FROM Wallet w JOIN FETCH w.user WHERE w.id = :walletId")
    Optional<Wallet> findByIdWithUser(Long walletId);

    //  Find wallets with low balance (useful for alerts)
    @Query("SELECT w FROM Wallet w WHERE w.balance < :threshold")
    List<Wallet> findAllWithLowBalance(double threshold);

    //  Find all wallets with a non-zero token balance
    @Query("SELECT w FROM Wallet w WHERE w.tokenBalance > 0")
    List<Wallet> findAllWithTokens();

    //  Count how many users have zero balance (analytics/statistics)
    @Query("SELECT COUNT(w) FROM Wallet w WHERE w.balance = 0")
    long countWalletsWithZeroBalance();


}
