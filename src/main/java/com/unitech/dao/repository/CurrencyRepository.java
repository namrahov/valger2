package com.unitech.dao.repository;

import com.unitech.dao.entity.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<CurrencyEntity, Long> {
    CurrencyEntity findByCurrency(String currency);
}
