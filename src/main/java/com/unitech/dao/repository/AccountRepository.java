package com.unitech.dao.repository;

import com.unitech.dao.entity.AccountEntity;
import com.unitech.dao.entity.UserEntity;
import com.unitech.model.enums.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    List<AccountEntity> findAllByIsActiveIsTrueAndUser(UserEntity user);

    AccountEntity findByUserAndAccountType(UserEntity user, AccountType accountType);
}
