package kr.ex.querydsl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import kr.ex.querydsl.dto.MemberDto;
import kr.ex.querydsl.entity.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static kr.ex.querydsl.entity.QMember.member;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class QueryDslTest3 {
    @Autowired
    EntityManager em;
    JPAQueryFactory query;

    @BeforeEach
    void initData(){
        // 쿼리DSL 객체
        query = new JPAQueryFactory(em);
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);
        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        // 영속성 컨텍스트 초기화
        em.flush();
        em.clear();
        System.out.println("====================");
    }

    @Test
    void dtoByJQPL(){
        List<MemberDto> result = em.createQuery(
                        "select new kr.ex.querydsl.entity.MemberDto(m.username, m.age) " +
                                "from Member m", MemberDto.class)
                .getResultList();
    }

    void dtoByQueryDslSetter접근(){
        List<MemberDto> result = query
                .select(Projections.bean(MemberDto.class,
                        member.username,
                        member.age))
                .from(member)
                .fetch();
    }

    @Test
    public void 동적쿼리_BooleanBuilder() throws Exception {
        String usernameParam = "member1";
        Integer ageParam = 10;
        List<Member> result = searchMember1(usernameParam, ageParam);
        
        result.forEach(m -> System.out.println("m = " + m));
        
        Assertions.assertThat(result.size()).isEqualTo(1);
    }
    private List<Member> searchMember1(String usernameCond, Integer ageCond) {
        BooleanBuilder builder = new BooleanBuilder(); // where 절을 동적으로 만들어준다
        if (usernameCond != null) {
            builder.and(member.username.eq(usernameCond));
        }
        if (ageCond != null) {
            builder.and(member.age.eq(ageCond));
        }
        return query
                .selectFrom(member)
                .where(builder)
                .fetch();
    }

    private List<Member> searchMember2(String usernameCond, Integer ageCond) {
        return query
                .selectFrom(member)
                .where(usernameEq(usernameCond), ageEq(ageCond))
                .fetch();
    }
    private BooleanExpression usernameEq(String usernameCond) {
        return usernameCond != null ? member.username.eq(usernameCond) : null;
    }
    private BooleanExpression ageEq(Integer ageCond) {
        return ageCond != null ? member.age.eq(ageCond) : null;
    }

    @Test
    @Commit
    public void bulkUpdate(){
        // 벌크 연산 => 한꺼번에 수정, 삭제 => 바로 db로 쿼리를 날린다 : flush()한다
        long count = query
                .update(member)
                .set(member.username, "비회원")
                .where(member.age.lt(28))
                .execute();

        // 영속성 컨테이너 초기화
        em.flush();
        em.clear();

        System.out.println("count = " + count);
        System.out.println("===================");
        List<Member> list = query.selectFrom(member).fetch();
        list.forEach(m -> System.out.println("m = " + m));

        assertThat(count).isEqualTo(2);
    }

//    //회원명, 팀명, 나이(ageGoe, ageLoe)
//    public List<MemberTeamDto> search(MemberSearchCondition condition) {
//        return query
//                .select(new QMemberTeamDto(
//                        member.id,
//                        member.username,
//                        member.age,
//                        team.id,
//                        team.name))
//                .from(member)
//                .leftJoin(member.team, team)
//                .where(usernameEq(condition.getUsername()),
//                        teamNameEq(condition.getTeamName()),
//                        ageGoe(condition.getAgeGoe()),
//                        ageLoe(condition.getAgeLoe()))
//                .fetch();
//    }
//    private BooleanExpression usernameEq(String username) {
//        return isEmpty(username) ? null : member.username.eq(username);
//    }
//    private BooleanExpression teamNameEq(String teamName) {
//        return isEmpty(teamName) ? null : team.name.eq(teamName);
//    }
//    private BooleanExpression ageGoe(Integer ageGoe) {
//        return ageGoe == null ? null : member.age.goe(ageGoe);
//    }
//    private BooleanExpression ageLoe(Integer ageLoe) {
//        return ageLoe == null ? null : member.age.loe(ageLoe);
//    }
}
