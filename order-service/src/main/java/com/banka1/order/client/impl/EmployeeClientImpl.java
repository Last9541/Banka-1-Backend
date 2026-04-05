package com.banka1.order.client.impl;

import com.banka1.order.client.EmployeeClient;
import com.banka1.order.dto.EmployeeDto;
import com.banka1.order.dto.EmployeePageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Optional;

@Component
@Profile("!local")
@RequiredArgsConstructor
@Slf4j
public class EmployeeClientImpl implements EmployeeClient {

    private final RestClient employeeRestClient;

    @Override
    public EmployeeDto getEmployee(Long id) {
        return employeeRestClient.get()
                .uri("/employees/{id}", id)
                .retrieve()
                .body(EmployeeDto.class);
    }

    @Override
    public EmployeePageResponse searchEmployees(String email, String ime, String prezime, String pozicija, int page, int size) {
        return employeeRestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/employees")
                        .queryParamIfPresent("email", Optional.ofNullable(email))
                        .queryParamIfPresent("ime", Optional.ofNullable(ime))
                        .queryParamIfPresent("prezime", Optional.ofNullable(prezime))
                        .queryParamIfPresent("pozicija", Optional.ofNullable(pozicija))
                        .queryParam("page", page)
                        .queryParam("size", size)
                        .build())
                .retrieve()
                .body(EmployeePageResponse.class);
    }
}
