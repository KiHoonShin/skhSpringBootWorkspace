package kr.study.jpa1.domain;

import jakarta.persistence.*;
import kr.study.jpa1.form.StudyForm;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

    // 도메인 영향을 미치는 비즈니스 로직, 생성자 로직

    // 생성자 메서드
    public static StudyRecord createRecord(StudyForm form , Member member){
        StudyRecord record = new StudyRecord();
        record.member = member;
        record.studyDay = form.getStudyDay();
        record.startTime = form.getStartTime();
        record.studyMins = form.getStudyMins();
        record.contents = form.getContents();

        return record;
    }

    public String getEndStudyDay(){
        String pattern = "yyyy/MM/dd HH:mm";
        LocalDateTime endStudyTime = LocalDateTime.of(this.studyDay, this.startTime);
        endStudyTime = endStudyTime.plusMinutes(this.studyMins);
        String data =  endStudyTime.format(DateTimeFormatter.ofPattern(pattern));

        return data;
    }

    public static StudyRecord modifyRecord(StudyForm form , StudyRecord record){

        record.studyDay = form.getStudyDay();
        record.startTime = form.getStartTime();
        record.studyMins = form.getStudyMins();
        record.contents = form.getContents();
        return record;
    }
}
