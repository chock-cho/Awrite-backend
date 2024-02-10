package Awrite_project.Awrite.web.dto.MyPageDTO;

import Awrite_project.Awrite.domain.User;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/* 프로필 사진 수정 DTO */
@Data
public class MyPageProfileRequestDTO {
    @NotNull
    @Min(value = 1, message = "프로필 사진 값은 1 이상이어야 합니다.")
    @Max(value = 4, message = "프로필 사진 값은 4 이하여야 합니다.")
    private Integer profileImg;

    public User toEntity(){
        return User.builder()
                .profilePicture(profileImg)
                .build();
    }
}
