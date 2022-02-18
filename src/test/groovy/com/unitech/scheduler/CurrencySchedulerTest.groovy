package com.unitech.scheduler

import com.unitech.service.CurrencyService
import spock.lang.Specification

class CurrencySchedulerTest extends Specification {
    private CurrencyService currencyService
    private CurrencyScheduler currencyScheduler

    def setup() {
        currencyService = Mock()
        currencyScheduler = new CurrencyScheduler(currencyService)
    }

    def "TestSaveCurrenciesFromClient"() {
        when:
        currencyScheduler.saveCurrenciesFromClient()

        then:
        1 * currencyService.saveCurrenciesFromClient()
    }
}
