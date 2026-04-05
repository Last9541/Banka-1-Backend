package com.banka1.order.client;

import com.banka1.order.dto.StockExchangeDto;
import com.banka1.order.dto.StockListingDto;

public interface StockClient {
    StockListingDto getListing(Long id);
    StockExchangeDto getStockExchange(Long id);
    Boolean isStockExchangeOpen(Long id);
}
