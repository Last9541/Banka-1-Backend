package com.banka1.order.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExchangeRateDto {
    private BigDecimal convertedAmount;
    private BigDecimal exchangeRate;
    private String fromCurrency;
    private String toCurrency;
}
