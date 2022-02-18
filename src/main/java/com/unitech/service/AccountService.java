package com.unitech.service;

import com.unitech.dao.entity.AccountEntity;
import com.unitech.dao.entity.UserEntity;
import com.unitech.dao.repository.AccountRepository;
import com.unitech.dao.repository.UserRepository;
import com.unitech.logger.DPLogger;
import com.unitech.mapper.AccountMapper;
import com.unitech.model.AccountDto;
import com.unitech.model.TransferDto;
import com.unitech.model.enums.AccountType;
import com.unitech.model.exception.AccountNotFoundException;
import com.unitech.model.exception.NotEnoughBalanceException;
import com.unitech.model.exception.UserNotFountException;
import com.unitech.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private static final DPLogger log = DPLogger.getLogger(AccountService.class);

    private final UserUtil userUtil;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public List<AccountDto> getUserAccountList(String authHeader) {
        log.info("ActionLog.getUserAccountList.start");
        UserEntity user = userUtil.findUserByToken(authHeader);
        List<AccountEntity> accounts = accountRepository.findAllByIsActiveIsTrueAndUser(user);

        log.info("ActionLog.getUserAccountList.end");
        return AccountMapper.buildAccountDtoList(accounts);
    }

    public void transfer(String authHeader, TransferDto transferDto) {
        log.info("ActionLog.transfer.start");

        UserEntity user = userUtil.findUserByToken(authHeader);
        AccountEntity fromAccount = getAccountIfExist(user, transferDto.getFromAccountType());

        isThereEnoughBalance(transferDto.getAmount(), fromAccount.getBalance());

        fromAccount.setBalance(fromAccount.getBalance().subtract(transferDto.getAmount()));

        UserEntity toUser = getUserIfExist(transferDto.getToPin());

        AccountEntity toAccount = getAccountIfExist(toUser, transferDto.getToAccountType());

        areAccountsSame(fromAccount, toAccount);

        toAccount.setBalance(toAccount.getBalance().add(transferDto.getAmount()));

        List<AccountEntity> accounts = new ArrayList<>();
        accounts.add(fromAccount);
        accounts.add(toAccount);

        accountRepository.saveAll(accounts);

        log.info("ActionLog.transfer.success");
    }

    private AccountEntity getAccountIfExist(UserEntity user, AccountType accountType) {
        AccountEntity account = accountRepository.findByUserAndAccountType(user, accountType);
        if (account == null) {
            throw new AccountNotFoundException("Account not found");
        }
        return account;
    }

    private void areAccountsSame(AccountEntity fromAccount, AccountEntity toAccount) {
        if (fromAccount.equals(toAccount)) {
            throw new AccountNotFoundException("The accounts are same");
        }
    }

    private void isThereEnoughBalance(BigDecimal transferAmount, BigDecimal currentBalance) {
        if (transferAmount.compareTo(currentBalance) > 0) {
            throw new NotEnoughBalanceException("There is no enough money in my account balance");
        }
    }

    private UserEntity getUserIfExist(String pin) {
        return userRepository.findByPin(pin).<UserNotFountException>orElseThrow(() -> {
            throw new UserNotFountException("ToUser not found");
        });
    }
}
