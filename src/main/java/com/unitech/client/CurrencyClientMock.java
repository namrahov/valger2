package com.unitech.client;

import com.unitech.model.CurrencyRateDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CurrencyClientMock  {

    public List<CurrencyRateDto> getCurrencies() {
        List<CurrencyRateDto> currencyList = new ArrayList<>();
        currencyList.add(new CurrencyRateDto("USD", BigDecimal.ONE));
        currencyList.add(new CurrencyRateDto("AZN", new BigDecimal("0.56")));
        currencyList.add(new CurrencyRateDto("TL", new BigDecimal("0.07")));

        return currencyList;
    }
}
