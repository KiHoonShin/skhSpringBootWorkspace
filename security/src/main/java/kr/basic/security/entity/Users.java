package kr.basic.security.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;
    @Enumerated(EnumType.STRING)
    private RoleUser role;
    @CreationTimestamp
    private Timestamp createDate;

//    @Builder
//    public Users(String username, String password, String email) {
//        this.username = username;
//        this.password = password;
//        this.email = email;
//        this.role = RoleUser.ROLE_USER;
//    }

    @Builder
    public Users(String username, String password, String email, RoleUser role, String provider, String providerId) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
    }

    //OAuth 를 위해 추가하는 필드
    private String provider;
    private String providerId;

}
