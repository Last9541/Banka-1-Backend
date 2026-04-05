package com.banka1.order.service;

import com.banka1.order.dto.ActuaryAgentDto;
import com.banka1.order.dto.SetLimitRequestDto;

import java.util.List;

public interface ActuaryService {
    List<ActuaryAgentDto> getAgents(String email, String ime, String prezime, String pozicija);
    void setLimit(Long employeeId, SetLimitRequestDto request);
    void resetLimit(Long employeeId);
    void resetAllLimits();
}
