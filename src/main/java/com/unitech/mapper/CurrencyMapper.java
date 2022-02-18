package com.unitech.mapper;

import com.unitech.dao.entity.CurrencyEntity;
import com.unitech.model.CurrencyRateDto;

import java.util.ArrayList;
import java.util.List;

public class CurrencyMapper {

    public static List<CurrencyEntity> mapDtoListToEntityList(List<CurrencyRateDto> dtos) {
        List<CurrencyEntity> currencies = new ArrayList<>();
        for (CurrencyRateDto dto :dtos) {
            CurrencyEntity entity = new CurrencyEntity();
            entity.setCurrency(dto.getCurrency());
            entity.setValueThanDollar(dto.getValueThanDollar());

            currencies.add(entity);
        }
        return currencies;
    }
}
