package com.basic.stock.service;

import com.basic.stock.entity.Stock;
import com.basic.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OptimisticLockStockService {
    private final StockRepository repository;

    @Transactional
    public Long decreaseStock(Long id, Long quantity){

        // stock 조회
        Stock stock = repository.findByIdWithOptimisticLock(id);

        // 재고 감소
        stock.decreaseStock(quantity);

        // 바로 갱신된 값을 db에 저장
        repository.saveAndFlush(stock);
        return stock.getQuantity();
    }
}
