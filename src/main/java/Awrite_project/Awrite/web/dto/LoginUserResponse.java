package Awrite_project.Awrite.web.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginUserResponse {

    @NotEmpty
    private int resultCode; // 성공시 1, 실패시 -1
    @NotEmpty
    private String msg;
}
