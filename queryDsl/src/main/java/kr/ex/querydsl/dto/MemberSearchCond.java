package kr.ex.querydsl.dto;

import lombok.Data;

@Data
public class MemberSearchCond {
    // 회원명 , 팀명 , 나이(크거나 , 작거나)
    private String username;
    private String teamName;
    private Integer ageGoe; //greater or equal
    private Integer ageLoe; // less or equal


}
