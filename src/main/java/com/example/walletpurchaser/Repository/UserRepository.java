package com.example.walletpurchaser.Repository;

import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;


import java.util.Optional;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //  Find by username or email
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    //  Check existence by username/email
    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    //  Find user by referral code
    Optional<User> findByReferralCode(String referralCode);

    //  get users with balance over a certain amount
    @Query("SELECT u FROM User u WHERE u.wallet.balance > :minBalance")
    List<User> findUsersWithWalletBalanceGreaterThan(double minBalance);

    //  Fetch user with subscriptions eagerly (JOIN FETCH for performance)
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.subscriptions WHERE u.id = :id")
    Optional<User> findByIdWithSubscriptions(Long id);

    //  Optional: Find all users with active subscriptions (custom logic assumed)
    @Query("SELECT DISTINCT u FROM User u JOIN u.subscriptions s WHERE s.endDate > CURRENT_DATE")
    List<User> findAllWithActiveSubscriptions();
}
