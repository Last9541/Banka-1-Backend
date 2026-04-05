package com.banka1.order.client;

import com.banka1.order.dto.AccountDetailsDto;

public interface AccountClient {
    AccountDetailsDto getAccountDetails(String accountNumber);
}
