package com.banka1.order.client;

import com.banka1.order.dto.CustomerDto;

public interface ClientClient {
    CustomerDto getCustomer(Long id);
}
