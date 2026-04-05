package com.banka1.order.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ActuaryAgentDto {
    private Long employeeId;
    private String ime;
    private String prezime;
    private String email;
    private String pozicija;
    private BigDecimal limit;
    private BigDecimal usedLimit;
    private Boolean needApproval;
}
