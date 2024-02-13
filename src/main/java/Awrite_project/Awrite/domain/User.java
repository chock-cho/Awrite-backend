package Awrite_project.Awrite.domain;

import Awrite_project.Awrite.domain.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.security.Timestamp;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DiscriminatorValue("UserType")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, length = 20)
    private String password;

    // 사용자의 닉네임
    @Getter
    @Column(nullable = false, length = 20)
    private String nickname;

    // 사용자의 프로필 (디폴트 값은 1)
    @Column(nullable = false, name = "profile_picture")
    @Min(value = 1, message = "Invalid profilePicture value. It must be between 1 and 4.")
    @Max(value = 4, message = "Invalid profilePicture value. It must be between 1 and 4.")
    private int profilePicture = 1; // 디폴트로 1로 설정

    @Builder
    public User(String email, String password, String nickname){
        super();  // Set discriminator value in the constructor
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.profilePicture = 1; // 프로필 사진 필드를 1로 초기화
    }

    // 사용자의 id 값 반환(고유한 값)
//    public String getUsername(){
//        return this.email;
//    }

    // 사용자의 패스워드 반환
//    public String getPassword(){
//        return this.password;
//    }

//    private ~~ profileImg;

}
