package com.example.walletpurchaser.controller;

import com.example.walletpurchaser.dto.SubscriptionRequestDTO;
import com.example.walletpurchaser.dto.SubscriptionResponseDTO;
import com.example.walletpurchaser.service.SubscriptionService;
import jakarta.validation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    // 🔹 Subscribe a user to a product
    @PostMapping("/subscribe")
    public ResponseEntity<SubscriptionResponseDTO> subscribe(@Valid @RequestBody SubscriptionRequestDTO request) {
        SubscriptionResponseDTO subscription = subscriptionService.subscribe(request);
        return ResponseEntity.ok(subscription);
    }

    // 🔹 Cancel a subscription
    @PostMapping("/cancel/{subscriptionId}")
    public ResponseEntity<String> cancelSubscription(@PathVariable Long subscriptionId) {
        subscriptionService.cancelSubscription(subscriptionId);
        return ResponseEntity.ok("Subscription canceled successfully.");
    }

    // 🔹 Get all subscriptions for a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SubscriptionResponseDTO>> getUserSubscriptions(@PathVariable Long userId) {
        List<SubscriptionResponseDTO> subscriptions = subscriptionService.getSubscriptionsByUserId(userId);
        return ResponseEntity.ok(subscriptions);
    }

    // 🔹 Get specific subscription by ID
    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(subscriptionService.getById(id));
    }
}