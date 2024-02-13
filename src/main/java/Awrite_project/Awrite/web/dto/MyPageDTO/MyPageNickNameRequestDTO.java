package Awrite_project.Awrite.web.dto.MyPageDTO;

import Awrite_project.Awrite.domain.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/* 프로필 닉네임 수정 DTO */
@Data
public class MyPageNickNameRequestDTO {
    @NotBlank(message = "닉네임은 공백이 아니어야 합니다.")
    private String nickname;

    public User toEntity(){
        return User.builder()
                .nickname(nickname)
                .build();
    }
}
