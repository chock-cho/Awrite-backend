package Awrite_project.Awrite.web.dto;

import Awrite_project.Awrite.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private long id;
    private String email;
    private String password;
    private String nickname;

    private Timestamp createDate;

    //@Builder
    public UserDto(int id, String email, String password, String nickname){
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.createDate = createDate;
    }

    // toEntity() 메서드를 이용해 Service에서 Database(Entity)로 Data를 전달할 때 Dto를 이용해서 전달할 수 있게끔
    public User toEntity(){
        User user = User.builder()
                .id(id)
                .email(email)
                .password(password)
                .nickname(nickname)
                .createDate(createDate)
                .build();
        return user;
    }
}
