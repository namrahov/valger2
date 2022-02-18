package com.unitech.model;

import com.unitech.model.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
    private Long id;
    private AccountType accountType;
    private BigDecimal balance;
    private boolean isActive;
    private String currency;
}
