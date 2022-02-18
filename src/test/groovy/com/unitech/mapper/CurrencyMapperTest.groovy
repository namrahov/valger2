package com.unitech.mapper

import com.unitech.model.CurrencyRateDto
import io.github.benas.randombeans.EnhancedRandomBuilder
import spock.lang.Specification

class CurrencyMapperTest extends Specification {

    private random = EnhancedRandomBuilder.aNewEnhancedRandom()

    def "TestMapDtoListToEntityList"() {
        given:
        def dtos = [random.nextObject(CurrencyRateDto)]

        when:
        def entities = CurrencyMapper.mapDtoListToEntityList(dtos)

        then:
        entities.get(0).currency == dtos.get(0).currency
        entities.get(0).valueThanDollar == dtos.get(0).valueThanDollar
    }
}
