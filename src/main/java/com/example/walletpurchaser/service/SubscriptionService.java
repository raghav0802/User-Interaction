package com.example.walletpurchaser.service;

import com.example.walletpurchaser.dto.SubscriptionRequestDTO;
import com.example.walletpurchaser.dto.SubscriptionResponseDTO;

import java.util.List;

public interface SubscriptionService {

    // 🔹 Create new subscription
    SubscriptionResponseDTO subscribe(SubscriptionRequestDTO request);

    // 🔹 Get all subscriptions for a user
    List<SubscriptionResponseDTO> getSubscriptionsByUserId(Long userId);

    // 🔹 Get a subscription by its ID
    SubscriptionResponseDTO getById(Long subscriptionId);

    // 🔹 Cancel subscription
    void cancelSubscription(Long subscriptionId);
}
