package kr.study.jpa1.form;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@ToString
@Builder // 생성할때만 초기 값 넣을 수 있음
public class StudyForm {
    private String memberLoginId;
    private LocalDate studyDay; // 미래시간 선택 x // 현재 , 과거 선택 o
    private LocalTime startTime; // 오후 6:10
    private int studyMins; // 40
    private String contents;
}
