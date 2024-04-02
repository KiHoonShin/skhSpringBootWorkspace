package com.basic.stock.service;

import com.basic.stock.entity.Stock;
import com.basic.stock.facade.NamedLockStockFacade;
import com.basic.stock.facade.OptimisticLockStockFacade;
import com.basic.stock.repository.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class NamedLockStockServiceTest {

    @Autowired
    private NamedLockStockFacade namedLockStockFacade; // 재시도 해주는 로직
    
    @Autowired
    private StockRepository repository;

    @BeforeEach
    public void insert(){

        // 상품 아이디가 1이고 수량이 100인 재고 한개 생성
        Stock stock = new Stock(1L, 100L);
        repository.saveAndFlush(stock);
    }


    @Test
    public void orderSametime100Stock() throws InterruptedException {

        int threadCount = 100; // 쓰레드를 100개 설정
        // 비동기를 편리하게 도와주는 클래스 : 동시에 32개 쓰레드 관리
        ExecutorService executorService = Executors.newFixedThreadPool(32);

        // 100개의 요청이 모두 끝날 때까지 기다려야 함으로 CounterDownLatch 통해서
        // 다른 쓰레드에서 수행되는 작업이 완료될때까지 대기할 수 있도록 도와주는 클래스
        CountDownLatch latch = new CountDownLatch(threadCount);

        for(int i = 0; i < threadCount; i+=1){

            executorService.submit(()->{
                try{
                    namedLockStockFacade.decreaseStock(1L , 1L);
                } catch (Exception e){
                    throw new RuntimeException(e);
                } finally{
                    latch.countDown();
                }
            });

        }

        latch.await(); // 모든 요청이 끝날 때까지 대기 -- 밑에 줄 실행 안함

        Stock stock = repository.findById(1L).orElseThrow();
        System.out.println("stock = " + stock);
        org.assertj.core.api.Assertions.assertThat(stock.getQuantity()).isEqualTo(0);
    }
}