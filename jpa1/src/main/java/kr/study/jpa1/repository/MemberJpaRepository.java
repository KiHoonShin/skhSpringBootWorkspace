package kr.study.jpa1.repository;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import kr.study.jpa1.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
public class MemberJpaRepository implements MemberRepository{

    private final EntityManager em;

//    @Autowired  --> @RequiredArgsConstructor 동일하다
//    public MemberJpaRepository(EntityManager em){
//        this.em = em;
//    }

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    public List<Member> findAll() {
//        List<Member> list = em.createQuery("select m from Member m", Member.class)
//                .getResultList();

        return  em.createQuery("select m from Member m", Member.class)
                .getResultList();

    }

    @Override
    public Member findById(Long id) {
        return null;
    }

    @Override
    public Member findByLoginId(String loginId) {
      List<Member> members = em.createQuery("select m from Member m where m.loginId = :loginId" , Member.class)
              .setParameter("loginId" , loginId)
              .getResultList();
        //return members == null ? null : members.get(0);
        return members.stream().findAny().orElse(null); // 객체가 있으면 객체를 반환 , 없으면 null 리턴
    }

    @Override
    public void deleteById(Long id) {
        // 삭제한 row 개수 = int
        int delCnt = em.createQuery("delete from Member m where m.id=:id")
                .setParameter("id", id)
                .executeUpdate();

        if(delCnt == 0){
            log.error("msg={}" , "삭제 실패 ");
        }
    }
}
