package com.basic.stock.facade;

import com.basic.stock.repository.RedisLockRepository;
import com.basic.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class LettuceLockStockFacade {
    private final RedisLockRepository repository;
    private final StockService service;

    // 실패 할 때 재시도 할 수 있는 로직

    public void decreaseStock(Long id, Long quantity) throws InterruptedException{

        // repository.lock(id) == true : lock 획득 했다 / == false : 획득 실패

        // lock을 획득할 때까지 반복문 돌리기
        while(!repository.lock(id)){
            Thread.sleep(100); // 좀 쉬었다가 다시 lock 획득 시도해
        }

        // 획득 성공 후 --> 재고 감소 로직 실행

        try{
            service.decreaseStock(id, quantity);
        } catch(Exception e){
            System.out.println(" 재고 감소 실패");
        } finally {
            repository.unLock(id);
        }

    }


}
