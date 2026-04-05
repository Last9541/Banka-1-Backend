package com.banka1.order.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SetLimitRequestDto {

    @NotNull
    @DecimalMin(value = "0.0")
    private BigDecimal limit;
}
