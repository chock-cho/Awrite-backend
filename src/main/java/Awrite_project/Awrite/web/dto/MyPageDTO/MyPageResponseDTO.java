package Awrite_project.Awrite.web.dto.MyPageDTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/* 프로필 정보 조회 */
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MyPageResponseDTO {
    @NotNull
    @Min(value = 1, message = "프로필 사진 값은 1 이상이어야 합니다.")
    @Max(value = 4, message = "프로필 사진 값은 4 이하여야 합니다.")
    private Integer profileImg;

    @NotNull
    private String nickname;

    @NotNull
    private String email;

}
