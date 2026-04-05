package com.banka1.order.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StockListingDto {
    private Long id;
    private String ticker;
    private String name;
    private BigDecimal price;
    private String currency;
    private Long exchangeId;
    private Integer contractSize;
}
