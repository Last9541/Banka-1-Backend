package com.banka1.order.dto;

import lombok.Data;

@Data
public class StockExchangeDto {
    private Long id;
    private String name;
    private String acronym;
    private String currency;
    private Boolean open;
}
