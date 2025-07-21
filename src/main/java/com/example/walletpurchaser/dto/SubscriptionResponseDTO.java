package com.example.walletpurchaser.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SubscriptionResponseDTO {

    private Long id;
    private Long userId;
    private Long productId;
    private long productName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;
    private boolean active;

}