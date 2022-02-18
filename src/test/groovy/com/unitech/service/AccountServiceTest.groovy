package com.unitech.service

import com.unitech.dao.entity.AccountEntity
import com.unitech.dao.entity.UserEntity
import com.unitech.dao.repository.AccountRepository
import com.unitech.dao.repository.UserRepository
import com.unitech.model.TransferDto
import com.unitech.model.enums.AccountType
import com.unitech.model.exception.AccountNotFoundException
import com.unitech.model.exception.NotEnoughBalanceException
import com.unitech.model.exception.UserNotFountException
import com.unitech.util.UserUtil
import io.github.benas.randombeans.EnhancedRandomBuilder
import spock.lang.Specification

class AccountServiceTest extends Specification {

    private random = EnhancedRandomBuilder.aNewEnhancedRandom()

    private UserUtil userUtil
    private AccountRepository accountRepository
    private UserRepository userRepository
    private AccountService accountService

    void setup() {
        userUtil = Mock()
        accountRepository = Mock()
        userRepository = Mock()
        accountService = new AccountService(userUtil, accountRepository, userRepository)
    }

    def "TestGetUserAccountList success"() {
        given:
        def authHeader = "Bearer sgsgsgwe"
        def user = random.nextObject(UserEntity)
        def accounts = [random.nextObject(AccountEntity)]

        when:
        accountService.getUserAccountList(authHeader)

        then:
        1 * userUtil.findUserByToken(authHeader) >> user
        1 * accountRepository.findAllByIsActiveIsTrueAndUser(user) >> accounts
    }

    def "TestTransfer success"() {
        given:
        def authHeader = "Bearer sgsgsgwe"
        def dto = random.nextObject(TransferDto)
        dto.amount = BigDecimal.valueOf(500)

        def user = random.nextObject(UserEntity)

        def fromAccount = random.nextObject(AccountEntity)
        fromAccount.balance = BigDecimal.valueOf(600)

        def toUser = random.nextObject(UserEntity)

        def account = random.nextObject(AccountEntity)

        when:
        accountService.transfer(authHeader, dto)

        then:
        1 * userUtil.findUserByToken(authHeader) >> user
        1 * accountRepository.findByUserAndAccountType(user, dto.fromAccountType) >> fromAccount
        fromAccount.setAccountType( AccountType.DEPOSIT_ACCOUNT)

        1 * userRepository.findByPin(dto.toPin) >>  Optional.of(toUser)

        1 * accountRepository.findByUserAndAccountType(toUser, dto.toAccountType) >> account
        fromAccount.setAccountType( AccountType.SALARY_ACCOUNT)

        1 * accountRepository.saveAll(_)
    }

    def "TestTransfer fromAccount not found"() {
        given:
        def authHeader = "Bearer sgsgsgwe"
        def dto = random.nextObject(TransferDto)
        dto.amount = BigDecimal.valueOf(500)

        def user = random.nextObject(UserEntity)

        when:
        accountService.transfer(authHeader, dto)

        then:
        1 * userUtil.findUserByToken(authHeader) >> user
        1 * accountRepository.findByUserAndAccountType(user, dto.fromAccountType) >> null
        0 * userRepository.findByPin(dto.toPin)
        0 * accountRepository.findByUserAndAccountType(_, dto.toAccountType)
        0 * accountRepository.saveAll(_)

        AccountNotFoundException ex = thrown()
        ex.message == "Account not found"
    }

    def "TestTransfer there is not enough balance"() {
        given:
        def authHeader = "Bearer sgsgsgwe"
        def dto = random.nextObject(TransferDto)
        dto.amount = BigDecimal.valueOf(500)

        def user = random.nextObject(UserEntity)

        def fromAccount = random.nextObject(AccountEntity)
        fromAccount.balance = BigDecimal.valueOf(400)

        when:
        accountService.transfer(authHeader, dto)

        then:
        1 * userUtil.findUserByToken(authHeader) >> user
        1 * accountRepository.findByUserAndAccountType(user, dto.fromAccountType) >> fromAccount
        0 * userRepository.findByPin(_)
        0 * accountRepository.findByUserAndAccountType(_, _)
        0 * accountRepository.saveAll(_)

        NotEnoughBalanceException ex = thrown()
        ex.message == "There is no enough money in my account balance"
    }

    def "TestTransfer toUser does not exist"() {
        given:
        def authHeader = "Bearer sgsgsgwe"
        def dto = random.nextObject(TransferDto)
        dto.amount = BigDecimal.valueOf(500)

        def user = random.nextObject(UserEntity)

        def fromAccount = random.nextObject(AccountEntity)
        fromAccount.balance = BigDecimal.valueOf(600)

        when:
        accountService.transfer(authHeader, dto)

        then:
        1 * userUtil.findUserByToken(authHeader) >> user
        1 * accountRepository.findByUserAndAccountType(user, dto.fromAccountType) >> fromAccount
        1 * userRepository.findByPin(dto.toPin) >> Optional.empty()
        0 * accountRepository.findByUserAndAccountType(_, _)
        0 * accountRepository.saveAll(_)

        UserNotFountException ex = thrown()
        ex.message == "ToUser not found"
    }

    def "TestTransfer toAccount does not exist"() {
        given:
        def authHeader = "Bearer sgsgsgwe"
        def dto = random.nextObject(TransferDto)
        dto.amount = BigDecimal.valueOf(500)

        def user = random.nextObject(UserEntity)

        def fromAccount = random.nextObject(AccountEntity)
        fromAccount.balance = BigDecimal.valueOf(600)

        def toUser = random.nextObject(UserEntity)

        when:
        accountService.transfer(authHeader, dto)

        then:
        1 * userUtil.findUserByToken(authHeader) >> user
        1 * accountRepository.findByUserAndAccountType(user, dto.fromAccountType) >> fromAccount
        1 * userRepository.findByPin(dto.toPin) >> Optional.of(toUser)
        1 * accountRepository.findByUserAndAccountType(toUser, dto.toAccountType) >> null
        0 * accountRepository.saveAll(_)

        AccountNotFoundException ex = thrown()
        ex.message == "Account not found"
    }

    def "TestTransfer same account"() {
        given:
        def authHeader = "Bearer sgsgsgwe"
        def dto = random.nextObject(TransferDto)
        dto.amount = BigDecimal.valueOf(500)

        def user = random.nextObject(UserEntity)

        def fromAccount = random.nextObject(AccountEntity)
        fromAccount.balance = BigDecimal.valueOf(600)

        def toUser = random.nextObject(UserEntity)

        def toAccount = fromAccount

        when:
        accountService.transfer(authHeader, dto)

        then:
        1 * userUtil.findUserByToken(authHeader) >> user
        1 * accountRepository.findByUserAndAccountType(user, dto.fromAccountType) >> fromAccount
        1 * userRepository.findByPin(dto.toPin) >> Optional.of(toUser)
        1 * accountRepository.findByUserAndAccountType(toUser, dto.toAccountType) >> toAccount
        0 * accountRepository.saveAll(_)

        AccountNotFoundException ex = thrown()
        ex.message == "The accounts are same"
    }
}
