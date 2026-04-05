package com.banka1.order.dto;

import lombok.Data;

import java.util.List;

@Data
public class EmployeePageResponse {
    private List<EmployeeDto> content;
    private int totalPages;
    private long totalElements;
}
