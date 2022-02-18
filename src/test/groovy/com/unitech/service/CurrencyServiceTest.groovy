package com.unitech.service

import com.unitech.client.CurrencyClientMock
import com.unitech.dao.entity.CurrencyEntity
import com.unitech.dao.repository.CurrencyRepository
import com.unitech.model.CurrencyDto
import com.unitech.model.CurrencyRateDto
import io.github.benas.randombeans.EnhancedRandomBuilder
import spock.lang.Specification

import java.math.RoundingMode

class CurrencyServiceTest extends Specification {

    private random = EnhancedRandomBuilder.aNewEnhancedRandom()

    private CurrencyRepository currencyRepository
    private CurrencyClientMock currencyClientMock
    private CurrencyService currencyService

    void setup() {
        currencyRepository = Mock()
        currencyClientMock = Mock()
        currencyService = new CurrencyService(currencyRepository, currencyClientMock)
    }

    def "TestFindCurrencyRate"() {
        given:
        def dto = new CurrencyDto("USD", "AZN")
        def fromCurrency = random.nextObject(CurrencyEntity)
        def toCurrency = random.nextObject(CurrencyEntity)

        when:
        def actual = currencyService.findCurrencyRate(dto)

        then:
        1 * currencyRepository.findByCurrency(dto.fromCurrency) >> fromCurrency
        1 * currencyRepository.findByCurrency(dto.toCurrency) >> toCurrency
        actual == fromCurrency.getValueThanDollar().divide(toCurrency.getValueThanDollar(), RoundingMode.HALF_DOWN)
    }

    def "TestSaveCurrenciesFromClient"() {
        given:
        def currencyDtos = [random.nextObject(CurrencyRateDto)]

        when:
        currencyService.saveCurrenciesFromClient()

        then:
        1 * currencyClientMock.getCurrencies() >> currencyDtos
        1 * currencyRepository.deleteAll()
        1 * currencyRepository.saveAll(_)
    }
}
