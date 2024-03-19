import entity.Customer;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class JpaMain {

    public static List<Customer> initCustomer(){
        List<Customer> list = new ArrayList<>();
        list.add(new Customer("ID100" , "test1"));
        list.add(new Customer("ID101" , "test2"));
        list.add(new Customer("ID102" , "test3"));
        list.add(new Customer("ID103" , "test4"));
        list.add(new Customer("ID104" , "test5"));
        list.add(new Customer("ID105" , "test6"));

        return list;
    }

    public static void main(String[] args){
        // 객체 sessionFactory
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("customer-ex");
        EntityManager em = emf.createEntityManager(); // session 객체
        EntityTransaction tx = em.getTransaction();

        tx.begin(); // start transaction;
        try {
            // 추가
//            Customer c1 = new Customer("ID007", "LEE7");
//            Customer c2 = new Customer("ID008", "HONG8");
//            em.persist(c1); // 영속성 컨텍스트에 객체를 저장 insert
//            em.persist(c2);  // insert
//
//           Customer findCustomer1 = em.find(Customer.class, "ID007"); // 이미 영속성 객체 안에 있으면 select 쿼리문 안 나감
//           Customer findCustomer2 = em.find(Customer.class, "ID005"); // 영속성 컨텍스트에 객체가 없을 때만 select문 나감
//           System.out.println("findCustomer1 = " + findCustomer1); // select 쿼리문 안 나감
//           System.out.println("findCustomer1 = " + findCustomer2); // select 쿼리문 나감

            // 삭제
//            Customer c1 = em.find(Customer.class,"ID001");
//            em.remove(c1); // sql 쓰기 지연에다가 쿼리문 저장
//            System.out.println("c1 = " + c1);

            // 영속성 컨테이너에 값을 저장하는 두가지 방법 em.persist() , em.find()
//            Customer findCustomer = em.find(Customer.class, "ID002");
//            System.out.println("findCustomer = " + findCustomer);
//
//            Customer c = new Customer("ID005" , "test"); // 비영속 상태
//            em.persist(c); // 영속 상태
//            em.detach(c); // 준영속상태
//
//            em.flush(); // db와 영속성 컨테이너의 데이터를 동기화 해준다 -> // 쓰기 지연 저장소에 있는 쿼리를 즉시 날린다
//            em.clear(); // 영속성 컨테이너 초기화 -> 쿼리문 안나감  준영속상태
//
//            System.out.println("================");
//
//            findCustomer.setName("신기훈"); // 변경 감지 : 최초 영속성 컨테이너에 저장 되어 있는 스냅샷 객체랑 비교
//                                            // 쓰기지연 저장소 update 저장
//            System.out.println("findCustomer = " + findCustomer);

            List<Customer> list = initCustomer();
            list.forEach(c -> em.persist(c));


            System.out.println("=========== start ============");

            // query 문 실행 전에 자동으로 em.flush()
            Query query = em.createQuery("select c from Customer  c" , Customer.class);
            List<Customer> customers = query.getResultList();

            System.out.println("=========== end ============");

            customers.forEach(c -> System.out.println("c = " + c));

            System.out.println("=======================");
            tx.commit(); // commit;  쓰기지연 저장소에 있는 sql 쿼리문 (insert, update, delete) 한꺼번에 나간다

        } catch(Exception e){
            tx.rollback();
        } finally{
            em.close();
            emf.close();
        }
    }

}
