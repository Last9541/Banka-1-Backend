package com.banka1.order.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDetailsDto {
    private String accountNumber;
    private BigDecimal balance;
    private String currency;
    private Long ownerId;
}
