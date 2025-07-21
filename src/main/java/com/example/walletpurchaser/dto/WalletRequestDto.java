package com.example.walletpurchaser.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WalletRequestDto {

    @NotNull(message = "User ID is required")
    private Long userId;

    @Min(value = 0, message = "Initial balance must be non-negative")
    private Double initialBalance;
}