package com.unitech.controller;

import com.unitech.model.CurrencyDto;
import com.unitech.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@CrossOrigin
@RestController
@RequestMapping("/v1/currency")
@RequiredArgsConstructor
public class CurrencyController {

    private final CurrencyService currencyService;

    @PostMapping("/rate")
    public BigDecimal findCurrencyRate(@RequestBody CurrencyDto dto) {
        return currencyService.findCurrencyRate(dto);
    }
}
