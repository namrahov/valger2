package com.unitech.mapper;

import com.unitech.dao.entity.RoleEntity;

import java.util.ArrayList;
import java.util.List;

public class RoleMapper {

    public static List<RoleEntity> buildRoleEntityList() {
        List<RoleEntity> roleEntities = new ArrayList<>();

        RoleEntity roleEntity = RoleEntity.builder()
                .name("ROLE_USER")
                .build();
        roleEntities.add(roleEntity);

        return roleEntities;
    }
}
