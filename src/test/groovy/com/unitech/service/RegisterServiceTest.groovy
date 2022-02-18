package com.unitech.service

import com.unitech.dao.entity.UserEntity
import com.unitech.dao.repository.UserRepository
import com.unitech.model.UserRegistrationDto
import com.unitech.model.exception.UserRegisterException
import io.github.benas.randombeans.EnhancedRandomBuilder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import spock.lang.Specification

class RegisterServiceTest extends Specification {
    private random = EnhancedRandomBuilder.aNewEnhancedRandom()

    private UserRepository userRepository
    private BCryptPasswordEncoder bCryptPasswordEncoder
    private RegisterService registerService

    void setup() {
        userRepository = Mock()
        bCryptPasswordEncoder = Mock()
        registerService = new RegisterService(userRepository, bCryptPasswordEncoder)
    }

    def "TestRegisterUser success"() {
        given:
        def dto = random.nextObject(UserRegistrationDto)

        when:
        registerService.registerUser(dto)

        then:
        1 * userRepository.findByPin(dto.getPin()) >> Optional.empty()
        1 * userRepository.save(_)
    }

    def "TestRegisterUser for exist user"() {
        given:
        def dto = random.nextObject(UserRegistrationDto)
        def userEntity = random.nextObject(UserEntity)

        when:
        registerService.registerUser(dto)

        then:
        1 * userRepository.findByPin(dto.getPin()) >> Optional.of(userEntity)
        0 * userRepository.save(_)

        UserRegisterException ex = thrown()
        ex.message == "User has already registered"
    }
}
