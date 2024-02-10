package Awrite_project.Awrite.web.dto.LoginDTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginUserResponseDTO {

    @NotEmpty
    private int resultCode; // 성공시 1, 실패시 -1
    @NotEmpty
    private String msg;
}
