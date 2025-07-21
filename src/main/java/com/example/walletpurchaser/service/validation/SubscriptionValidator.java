package com.example.walletpurchaser.service.validation;


import com.example.model.Subscription;
import com.example.walletpurchaser.exception.SubscriptionValidationException;
import com.example.walletpurchaser.Repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SubscriptionValidator {

    private final SubscriptionRepository subscriptionRepository;

    public void validateUserNotSubscribed(Long userId, Long productId) {
        Optional<Subscription> existing = subscriptionRepository.findByUserIdAndProductId(userId, productId);

        if (existing.isPresent() && existing.get().isActive()) {
            throw new SubscriptionValidationException("User already has an active subscription for this product.");
        }
    }

    public void validateSubscriptionDates(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null || end.isBefore(start)) {
            throw new SubscriptionValidationException("Invalid subscription start or end date.");
        }
    }

    public void validateSubscriptionIsActive(Subscription subscription) {
        if (!subscription.isActive()) {
            throw new SubscriptionValidationException("Subscription is already inactive.");
        }
    }

    // Future use: Validate product eligibility, usage limits, etc.
}