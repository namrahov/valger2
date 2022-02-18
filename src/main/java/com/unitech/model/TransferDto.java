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
public class TransferDto {
    private AccountType fromAccountType;
    private String toPin;
    private AccountType toAccountType;
    private BigDecimal amount;
    private String currency;
}
