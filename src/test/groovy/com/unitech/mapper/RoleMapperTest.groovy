package com.unitech.mapper

import spock.lang.Specification

class RoleMapperTest extends Specification {

    def "TestBuildRoleEntityList"() {
        when:
        def entities = RoleMapper.buildRoleEntityList()

        then:
        entities.get(0).name == "ROLE_USER"
    }
}
