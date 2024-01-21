package Awrite_project.Awrite.domain;

import Awrite_project.Awrite.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.security.Timestamp;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, length = 20)
    private String password;

    // 사용자의
    @Getter
    @Column(nullable = false, length = 20)
    private String nickname;


    @Builder
    public User(String email, String password, String nickname){
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    // 사용자의 id 값 반환(고유한 값)
    public String getUsername(){
        return this.email;
    }

    // 사용자의 패스워드 반환
    public String getPassword(){
        return this.password;
    }


    @CreationTimestamp // 시간이 자동 입력
    private Timestamp createDate;
//    private ~~ profileImg;

}
