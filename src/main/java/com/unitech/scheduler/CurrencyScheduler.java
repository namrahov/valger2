package com.unitech.scheduler;

import com.unitech.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.core.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CurrencyScheduler {

    private final CurrencyService currencyService;

    @Scheduled(cron = "*/60 * * * * *")
    @SchedulerLock(name = "save-currencies-from-client", lockAtLeastForString = "PT15M", lockAtMostForString = "PT30M")
    public void saveCurrenciesFromClient() {
        currencyService.saveCurrenciesFromClient();
    }
}
