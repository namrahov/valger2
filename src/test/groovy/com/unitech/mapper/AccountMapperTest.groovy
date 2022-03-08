package com.unitech.mapper


import io.github.benas.randombeans.EnhancedRandomBuilder
import spock.lang.Specification

class AccountMapperTest extends Specification {
    private random = EnhancedRandomBuilder.aNewEnhancedRandom()

    def "TestBuildAccountDtoList"() {
        given:
        def entities = [random.nextObject(AccountEntity)]

        when:
        def dtos = AccountMapper.buildAccountDtoList(entities)

        then:
        entities.get(0).id == dtos.get(0).id
        entities.get(0).accountType == dtos.get(0).accountType
        entities.get(0).balance == dtos.get(0).balance
        entities.get(0).currency == dtos.get(0).currency
    }
}
