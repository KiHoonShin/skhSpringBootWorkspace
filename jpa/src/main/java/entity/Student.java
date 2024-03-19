package entity;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@Table(name="student_tbl")
@ToString(exclude = "major")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentid;
    private String name;
    private String grade;               // fetch = FetchType.LAZY 지연 로딩
    @ManyToOne(fetch = FetchType.LAZY)// 관계 구성 FetchType.EAGER(기본값) : 즉시로딩 : 연관되어있는 모든
    @JoinColumn(name="majorId") // 테이블 컬럼의 fk명
    private Major major;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true) // 테이블 컬럼의 fk명 // 이름 안주면 클래스이름_id로 생성됨
    private Locker locker; // locker_id 1

    public Student(String name, String grade) {
        this.name = name;
        this.grade = grade;
    }
}
