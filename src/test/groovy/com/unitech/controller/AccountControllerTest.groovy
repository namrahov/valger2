package com.unitech.controller

import com.unitech.model.AccountDto
import com.unitech.model.TransferDto
import com.unitech.model.enums.AccountType
import com.unitech.service.AccountService
import org.skyscreamer.jsonassert.JSONAssert
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.http.HttpHeaders.AUTHORIZATION

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

class AccountControllerTest extends Specification {

    private AccountService accountService
    private MockMvc mockMvc

    void setup() {
        accountService = Mock()
        def accountController = new AccountController(accountService)
        mockMvc = MockMvcBuilders.standaloneSetup(accountController)
                .setControllerAdvice(new ErrorHandler())
                .build()
    }

    def "TestGetUserAccountList"() {
        given:
        def authHeader = "Bearer etgsgs"
        def url = "/v1/account/all"

        def accountDto = new AccountDto(
                1,
                AccountType.SALARY_ACCOUNT,
                BigDecimal.valueOf(200),
                true,
                "AZN"
        )

        def responseView = [accountDto]

        def expectedResponse = '''
            [
               {
                   "id": 1,
                   "accountType": "SALARY_ACCOUNT",
                   "balance": 200,
                   "currency" : "AZN",
                   "active": true                       
               }
           ]
        '''

        when:
        def result = mockMvc.perform(get(url)
                .header(AUTHORIZATION, authHeader)
        ).andReturn()

        then:
        1 * accountService.getUserAccountList(authHeader) >> responseView

        def response = result.response
        response.status == HttpStatus.OK.value()
        JSONAssert.assertEquals(expectedResponse, response.getContentAsString(), false)
    }

    def "TestTransfer"() {
        given:
        def authHeader = "Bearer sfsd"
        def url = "/v1/account/transfer"

        def transferDto = new TransferDto(
                AccountType.SALARY_ACCOUNT,
                "1234567",
                AccountType.DEPOSIT_ACCOUNT,
                BigDecimal.valueOf(100),
                "AZN"
        )

        def requestJson = ''' {
                "fromAccountType": "SALARY_ACCOUNT",
                "toPin": "1234567",
                "toAccountType": "DEPOSIT_ACCOUNT",
                "amount": 100,
                "currency": "AZN"
                }
        '''

        when:
        def result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .header(AUTHORIZATION, authHeader)
        ).andReturn()

        then:
        1 * accountService.transfer(authHeader, transferDto)

        def response = result.response
        response.status == HttpStatus.NO_CONTENT.value()
    }
}
