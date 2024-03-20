package kr.study.jpa1.form;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder // 생성할때만 초기 값 넣을 수 있음
public class StudyForm {
    private String memberId;
    private String studyDay; // 미래시간 선택 x // 현재 , 과거 선택 o
    private String startTime; // 오후 6:10
    private int studyMins; // 40
    private String contents;
}
