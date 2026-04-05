package com.banka1.order.client;

import com.banka1.order.dto.ExchangeRateDto;

import java.math.BigDecimal;

public interface ExchangeClient {
    ExchangeRateDto calculate(String fromCurrency, String toCurrency, BigDecimal amount);
}
