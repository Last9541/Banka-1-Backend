package com.banka1.order.client;

import com.banka1.order.dto.EmployeeDto;
import com.banka1.order.dto.EmployeePageResponse;

public interface EmployeeClient {
    EmployeeDto getEmployee(Long id);
    EmployeePageResponse searchEmployees(String email, String ime, String prezime, String pozicija, int page, int size);
}
