package com.banka1.order.dto;

import lombok.Data;

@Data
public class EmployeeDto {
    private Long id;
    private String ime;
    private String prezime;
    private String email;
    private String username;
    private String pozicija;
    private String departman;
    private boolean aktivan;
    private String role;
}
