package kr.study.jpa1.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@ToString
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studyId; // study_id
    private LocalDate studyDay; // 미래시간 선택 x // 현재 , 과거 선택 o
    private LocalTime startTime; // 오후 6:10
    private int studyMins; // 40
    @Lob
    private String contents;

    // fk 값을 가지는가 ? (member_id) , == 연관관계의 주인 == @ManyToOne
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="memberId")
    private Member member; // fk => member_id
}
