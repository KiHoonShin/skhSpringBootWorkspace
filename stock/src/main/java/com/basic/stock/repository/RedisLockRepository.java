package com.basic.stock.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RedisLockRepository {
    private RedisTemplate<String , String> redisTemplate; // 레디스 명령어를 변경해주는 템플릿 라이브러리 클래스
    public Boolean lock(Long key){  // setnx 1 lock 3000
        return redisTemplate.opsForValue()  // key 라는 값으로 lock을 거는데 3초동안 점유해라. 라는 redis 명령어를 라이브러리로 구현한 것
                .setIfAbsent(getGenerateKey(key), "lock" , Duration.ofMillis(3_000));

    }

    public Boolean unLock(Long key){ // del 1
        return redisTemplate.delete(getGenerateKey(key));
    }

    private String getGenerateKey(Long key) {
        return key.toString();
    }

}
