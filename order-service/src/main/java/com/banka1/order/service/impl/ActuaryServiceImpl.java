package com.banka1.order.service.impl;

import com.banka1.order.client.EmployeeClient;
import com.banka1.order.dto.ActuaryAgentDto;
import com.banka1.order.dto.EmployeeDto;
import com.banka1.order.dto.EmployeePageResponse;
import com.banka1.order.dto.SetLimitRequestDto;
import com.banka1.order.entity.ActuaryInfo;
import com.banka1.order.repository.ActuaryInfoRepository;
import com.banka1.order.service.ActuaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActuaryServiceImpl implements ActuaryService {

    private final ActuaryInfoRepository actuaryInfoRepository;
    private final EmployeeClient employeeClient;

    @Override
    public List<ActuaryAgentDto> getAgents(String email, String ime, String prezime, String pozicija) {
        EmployeePageResponse page = employeeClient.searchEmployees(email, ime, prezime, pozicija, 0, 200);

        return page.getContent().stream()
                .filter(emp -> "AGENT".equals(emp.getRole()))
                .map(emp -> {
                    ActuaryInfo info = actuaryInfoRepository.findByEmployeeId(emp.getId())
                            .orElseGet(() -> createDefaultActuaryInfo(emp.getId()));
                    return toDto(emp, info);
                })
                .toList();
    }

    @Override
    @Transactional
    public void setLimit(Long employeeId, SetLimitRequestDto request) {
        EmployeeDto employee = employeeClient.getEmployee(employeeId);

        if ("ADMIN".equals(employee.getRole())) {
            throw new IllegalArgumentException("Nije moguće menjati limit adminu.");
        }
        if (!"AGENT".equals(employee.getRole())) {
            throw new IllegalArgumentException("Limit se može postaviti samo agentu.");
        }

        ActuaryInfo info = actuaryInfoRepository.findByEmployeeId(employeeId)
                .orElseGet(() -> createDefaultActuaryInfo(employeeId));

        info.setLimit(request.getLimit());
        actuaryInfoRepository.save(info);
    }

    @Override
    @Transactional
    public void resetLimit(Long employeeId) {
        EmployeeDto employee = employeeClient.getEmployee(employeeId);

        if ("ADMIN".equals(employee.getRole())) {
            throw new IllegalArgumentException("Nije moguće resetovati limit adminu.");
        }

        ActuaryInfo info = actuaryInfoRepository.findByEmployeeId(employeeId)
                .orElseGet(() -> createDefaultActuaryInfo(employeeId));

        info.setUsedLimit(BigDecimal.ZERO);
        actuaryInfoRepository.save(info);
    }

    @Override
    @Transactional
    public void resetAllLimits() {
        log.info("Automatski reset usedLimit za sve agente.");
        List<ActuaryInfo> all = actuaryInfoRepository.findAll();
        for (ActuaryInfo info : all) {
            info.setUsedLimit(BigDecimal.ZERO);
        }
        actuaryInfoRepository.saveAll(all);
    }

    private ActuaryInfo createDefaultActuaryInfo(Long employeeId) {
        ActuaryInfo info = new ActuaryInfo();
        info.setEmployeeId(employeeId);
        info.setUsedLimit(BigDecimal.ZERO);
        info.setNeedApproval(false);
        return actuaryInfoRepository.save(info);
    }

    private ActuaryAgentDto toDto(EmployeeDto emp, ActuaryInfo info) {
        ActuaryAgentDto dto = new ActuaryAgentDto();
        dto.setEmployeeId(emp.getId());
        dto.setIme(emp.getIme());
        dto.setPrezime(emp.getPrezime());
        dto.setEmail(emp.getEmail());
        dto.setPozicija(emp.getPozicija());
        dto.setLimit(info.getLimit());
        dto.setUsedLimit(info.getUsedLimit());
        dto.setNeedApproval(info.getNeedApproval());
        return dto;
    }
}
