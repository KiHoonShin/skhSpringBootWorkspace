import entity.Customer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args){
        // 객체 sessionFactory
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("customer-ex");
        EntityManager em = emf.createEntityManager(); // session 객체
        EntityTransaction tx = em.getTransaction();

        tx.begin(); // start transaction;
        try {
//            Customer c1 = new Customer("ID007", "LEE7");
//            Customer c2 = new Customer("ID008", "HONG8");
//            em.persist(c1); // 영속성 컨텍스트에 객체를 저장 insert
//            em.persist(c2);  // insert
//
//           Customer findCustomer1 = em.find(Customer.class, "ID007"); // 이미 영속성 객체 안에 있으면 select 쿼리문 안 나감
//           Customer findCustomer2 = em.find(Customer.class, "ID005"); // 영속성 컨텍스트에 객체가 없을 때만 select문 나감
//           System.out.println("findCustomer1 = " + findCustomer1); // select 쿼리문 안 나감
//           System.out.println("findCustomer1 = " + findCustomer2); // select 쿼리문 나감

            Customer c1 = em.find(Customer.class,"ID001");
            em.remove(c1); // sql 쓰기 지연에다가 쿼리문 저장
            System.out.println("c1 = " + c1);

            tx.commit(); // commit;  쓰기지연 저장소에 있는 sql 쿼리문 (insert, update, delete) 한꺼번에 나간다

        } catch(Exception e){
            tx.rollback();
        } finally{
            em.close();
            emf.close();
        }
    }

}
