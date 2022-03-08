package com.unitech.controller

import com.unitech.model.CurrencyDto

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

class CurrencyControllerTest extends Specification {

    private CurrencyService currencyService
    private MockMvc mockMvc

    void setup() {
        currencyService = Mock()
        def currencyController = new CurrencyController(currencyService)
        mockMvc = MockMvcBuilders.standaloneSetup(currencyController)
                .setControllerAdvice(new ErrorHandler())
                .build()
    }

    def "TestFindCurrencyRate"() {
        given:
        def url = "/v1/currency/rate"

        def currencyDto = new CurrencyDto("USD", "AZN")

        def rate = BigDecimal.valueOf(0.56)

        def requestJson = '''
            {
                "fromCurrency": "USD",
                "toCurrency": "AZN"              
            }
        '''

        when:
        def result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        ).andReturn()

        then:
        1 * currencyService.findCurrencyRate(currencyDto) >> rate

        def response = result.response
        response.status == HttpStatus.OK.value()
    }
}
