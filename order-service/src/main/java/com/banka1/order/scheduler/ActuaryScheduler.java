package com.banka1.order.scheduler;

import com.banka1.order.service.ActuaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class ActuaryScheduler {

    private final ActuaryService actuaryService;

    @Scheduled(cron = "0 59 23 * * *")
    public void resetDailyLimits() {
        log.info("Pokretanje dnevnog reseta usedLimit za sve agente.");
        actuaryService.resetAllLimits();
    }
}
