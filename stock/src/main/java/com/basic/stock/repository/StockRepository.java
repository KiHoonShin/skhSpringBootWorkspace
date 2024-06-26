package com.basic.stock.repository;

import com.basic.stock.entity.Stock;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


public interface StockRepository extends JpaRepository<Stock, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from Stock s where s.id=:id")
    Stock findByIdWithPessimissticLock(Long id);

    @Lock(LockModeType.OPTIMISTIC)
    @Query("select s from Stock s where s.id=:id")
    Stock findByIdWithOptimisticLock(Long id);
}
