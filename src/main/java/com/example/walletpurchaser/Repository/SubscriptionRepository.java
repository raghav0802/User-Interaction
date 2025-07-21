package com.example.walletpurchaser.Repository;

import com.example.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    //  Find all subscriptions of a user
    List<Subscription> findByUserId(Long userId);

    //  Find active subscriptions of a user
    @Query("SELECT s FROM Subscription s WHERE s.user.id = :userId AND s.startDate <= CURRENT_TIMESTAMP AND s.endDate >= CURRENT_TIMESTAMP")
    List<Subscription> findActiveSubscriptionsByUserId(Long userId);

    // Check if user has at least one active subscription
    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Subscription s WHERE s.user.id = :userId AND s.startDate <= CURRENT_TIMESTAMP AND s.endDate >= CURRENT_TIMESTAMP")
    boolean existsActiveSubscriptionByUserId(Long userId);

    //  Find expired subscriptions
    @Query("SELECT s FROM Subscription s WHERE s.endDate < CURRENT_TIMESTAMP")
    List<Subscription> findExpiredSubscriptions();

    //  Subscriptions started in a specific date range (reporting)
    List<Subscription> findByStartDateBetween(LocalDateTime start, LocalDateTime end);

    //  Find subscription by user and product (if you want to prevent duplicate subscriptions)
    Optional<Subscription> findByUserIdAndProductId(Long userId, Long productId);
}