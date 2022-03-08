package com.unitech.controller

import com.unitech.model.UserRegistrationDto

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

class RegisterControllerTest extends Specification {
    private RegisterService registerService
    private MockMvc mockMvc

    void setup() {
        registerService = Mock()
        def registerController = new RegisterController(registerService)
        mockMvc = MockMvcBuilders.standaloneSetup(registerController)
                .setControllerAdvice(new ErrorHandler())
                .build()
    }

    def "TestRegisterUser"() {
        given:
        def url = "/v1/register"

        def userRegistrationDto = new UserRegistrationDto("1234567", "123")

        def requestJson = '''
            {
                "pin": "1234567",
                "password": "123"              
            }
        '''

        when:
        def result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
        ).andReturn()

        then:
        1 * registerService.registerUser(userRegistrationDto)

        def response = result.response
        response.status == HttpStatus.CREATED.value()
    }
}
