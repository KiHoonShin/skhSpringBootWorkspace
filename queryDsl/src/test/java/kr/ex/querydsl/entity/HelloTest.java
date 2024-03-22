package kr.ex.querydsl.entity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest // 통합 테스트 : 스프링 부트 시작처럼 root-context : db 객체 -> em
@Transactional // 트렌잭션을 열어야지만 db에 값 전달 가능 Transactional.commit() -> rollback() : 테스트만 하고 db에 값은 저장 안 된다
class HelloTest {
    @Autowired
    EntityManager em;

    @Test
    //@Commit // commit을 하면 rollback 안 하고 진짜 db에 저장이 된다
    void firstTest(){
        Hello hello1 = new Hello();
        Hello hello2 = new Hello();
        Hello hello3 = new Hello();
        Hello hello4 = new Hello();
        em.persist(hello1);
        em.persist(hello2);
        em.persist(hello3);
        em.persist(hello4);
        System.out.println("=========================");
       List<Hello> list = em.createQuery("select h from Hello h").getResultList();

       list.forEach(h -> System.out.println("h = " + h));
    }

    @Test
    void firstQueryDsl(){
        Hello hello1 = new Hello();
        Hello hello2 = new Hello();
        Hello hello3 = new Hello();
        Hello hello4 = new Hello();
        em.persist(hello1);
        em.persist(hello2);
        em.persist(hello3);
        em.persist(hello4);
        System.out.println("=========================");
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QHello h = new QHello("hello");
        
        List<Hello> list = queryFactory.select(h.hello).from(h.hello).fetch();
        // QHello 클래스 안에 미리 static final hello 객체를 만들어 놨다
        Hello findHello = queryFactory.selectFrom(h.hello).fetchFirst();

        System.out.println("findHello = " + findHello);

       //assertThat()

        list.forEach(h2 -> System.out.println("h2 = " + h2));
    }
}