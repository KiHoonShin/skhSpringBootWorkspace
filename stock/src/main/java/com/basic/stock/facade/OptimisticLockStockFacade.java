package com.basic.stock.facade;

import com.basic.stock.service.OptimisticLockStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


//facade : 건물 외벽

// optimistic lock 따로 데이터베이스를 락을 걸지 않기때문에
// 우리가 재시도 할 수 있는 로직(: 재고 감소 )을 따로 작성을 해야한다

// version이 틀릴때 재시도 하는 로직
@Service
@RequiredArgsConstructor
public class OptimisticLockStockFacade {
    private final OptimisticLockStockService service;

    public void decreaseStock(Long id, Long quantity) throws InterruptedException {
        while(true){
            try{
                // 접속 성공 = 현재 db랑 version이 맞으면
                service.decreaseStock(id, quantity);
                return;
            } catch (Exception e){
                System.out.println("접속 실패");
                Thread.sleep(50);
            }
        }
    }
}
