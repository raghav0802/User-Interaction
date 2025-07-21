package com.example.walletpurchaser.service.impl;

import com.example.walletpurchaser.dto.SubscriptionRequestDTO;
import com.example.walletpurchaser.dto.SubscriptionResponseDTO;
import com.example.walletpurchaser.exception.ResourceNotFoundException;
import com.example.model.Product;
import com.example.model.SubscriptionStatus;
import com.example.model.Subscription;
import com.example.model.TransactionStatus;
import com.example.model.User;
import com.example.walletpurchaser.Repository.ProductRepository;
import com.example.walletpurchaser.Repository.SubscriptionRepository;
import com.example.walletpurchaser.Repository.UserRepository;
import com.example.walletpurchaser.service.SubscriptionService;
import com.example.walletpurchaser.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final WalletService walletService;

    @Override
    @Transactional
    public SubscriptionResponseDTO subscribe(SubscriptionRequestDTO request) {
        Long userId = request.getUserId();
        Long productId = request.getProductId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        // Check for existing active subscription
        subscriptionRepository.findByUserIdAndProductId(userId, productId).ifPresent(sub -> {
            throw new IllegalStateException("User already subscribed to this product.");
        });

        // Deduct from wallet
        if (!walletService.hasSufficientBalance(userId, product.getPrice())) {
            throw new IllegalStateException("Insufficient wallet balance.");
        }

        walletService.deductFunds(userId, product.getPrice());

        Subscription subscription = Subscription.builder()
                .user(user)
                .product(product)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(product.getActiveDays()))
                .status(SubscriptionStatus.ACTIVE)
                .canceled(false)
                .build();

        subscription = subscriptionRepository.save(subscription);

        return mapToResponseDTO(subscription);
    }

    @Override
    public List<SubscriptionResponseDTO> getSubscriptionsByUserId(Long userId) {
        return subscriptionRepository.findByUserId(userId)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SubscriptionResponseDTO getById(Long subscriptionId) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found"));
        return mapToResponseDTO(subscription);
    }

    @Override
    public void cancelSubscription(Long subscriptionId) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found"));

        subscription.setActive(false);
        subscription.setStatus(TransactionStatus.FAILED);
        subscription.setEndDate(LocalDateTime.now());

        subscriptionRepository.save(subscription);
    }

    // 🧾 Manual mapper
    private SubscriptionResponseDTO mapToResponseDTO(Subscription sub) {
        return SubscriptionResponseDTO.builder()
                .id(sub.getId())
                .userId(sub.getUser().getId())
                .productId(sub.getProduct().getId())
                .productName(Long.parseLong(sub.getProduct().getName()))
                .startDate(sub.getStartDate())
                .endDate(sub.getEndDate())
                .active(sub.isActive())
                .status(String.valueOf(sub.getStatus()))
                .build();
    }
}