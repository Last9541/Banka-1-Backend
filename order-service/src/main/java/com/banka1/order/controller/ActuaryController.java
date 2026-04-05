package com.banka1.order.controller;

import com.banka1.order.dto.ActuaryAgentDto;
import com.banka1.order.dto.SetLimitRequestDto;
import com.banka1.order.service.ActuaryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/actuaries")
@RequiredArgsConstructor
public class ActuaryController {

    private final ActuaryService actuaryService;

    @GetMapping("/agents")
    @PreAuthorize("hasRole('SUPERVISOR')")
    public ResponseEntity<List<ActuaryAgentDto>> getAgents(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String ime,
            @RequestParam(required = false) String prezime,
            @RequestParam(required = false) String pozicija
    ) {
        return ResponseEntity.ok(actuaryService.getAgents(email, ime, prezime, pozicija));
    }

    @PutMapping("/agents/{id}/limit")
    @PreAuthorize("hasRole('SUPERVISOR')")
    public ResponseEntity<Void> setLimit(
            @PathVariable Long id,
            @RequestBody @Valid SetLimitRequestDto request
    ) {
        actuaryService.setLimit(id, request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/agents/{id}/reset-limit")
    @PreAuthorize("hasRole('SUPERVISOR')")
    public ResponseEntity<Void> resetLimit(@PathVariable Long id) {
        actuaryService.resetLimit(id);
        return ResponseEntity.ok().build();
    }
}
