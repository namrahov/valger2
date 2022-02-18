package com.unitech.service;

import com.unitech.client.CurrencyClientMock;
import com.unitech.dao.entity.CurrencyEntity;
import com.unitech.dao.repository.CurrencyRepository;
import com.unitech.logger.DPLogger;
import com.unitech.mapper.CurrencyMapper;
import com.unitech.model.CurrencyDto;
import com.unitech.model.CurrencyRateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyService {
    private static final DPLogger log = DPLogger.getLogger(CurrencyService.class);

    private final CurrencyRepository currencyRepository;
    private final CurrencyClientMock mock;

    public BigDecimal findCurrencyRate(CurrencyDto dto) {
        log.info("ActionLog.findCurrencyRate.start");

        CurrencyEntity fromCurrency = currencyRepository.findByCurrency(dto.getFromCurrency());
        CurrencyEntity toCurrency = currencyRepository.findByCurrency(dto.getToCurrency());

        log.info("ActionLog.findCurrencyRate.end");
        return fromCurrency.getValueThanDollar().divide(toCurrency.getValueThanDollar(), RoundingMode.HALF_DOWN);
    }

    @Transactional
    public void saveCurrenciesFromClient() {
        log.info("ActionLog.saveCurrenciesFromClient.start");
        List<CurrencyRateDto> currencyDtos = mock.getCurrencies();

        currencyRepository.deleteAll();

        currencyRepository.saveAll(CurrencyMapper.mapDtoListToEntityList(currencyDtos));
        log.info("ActionLog.saveCurrenciesFromClient.success");
    }
}
