package com.example.walletpurchaser.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class WalletResponseDto {
    private long walletId;
    private long userId;
    private BigDecimal balance;
}
