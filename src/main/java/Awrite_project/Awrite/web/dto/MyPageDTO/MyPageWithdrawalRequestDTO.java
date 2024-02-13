package Awrite_project.Awrite.web.dto.MyPageDTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
/* 회원 탈퇴 요청 DTO */
public class MyPageWithdrawalRequestDTO {
    @NotNull
    private String password;

}
