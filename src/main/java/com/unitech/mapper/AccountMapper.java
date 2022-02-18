package com.unitech.mapper;

import com.unitech.dao.entity.AccountEntity;
import com.unitech.model.AccountDto;

import java.util.ArrayList;
import java.util.List;

public class AccountMapper {

    public static List<AccountDto> buildAccountDtoList(List<AccountEntity> accounts) {
        List<AccountDto> accountDtos = new ArrayList<>();

        for (AccountEntity entity : accounts){
            AccountDto dto = new AccountDto();
            dto.setId(entity.getId());
            dto.setAccountType(entity.getAccountType());
            dto.setBalance(entity.getBalance());
            dto.setCurrency(entity.getCurrency());
            dto.setActive(entity.isActive());

            accountDtos.add(dto);
        }

        return accountDtos;
    }
}
