package Awrite_project.Awrite.web.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginUserRequest {

    @NotEmpty
    private String loginEmail;
    @NotEmpty
    private String loginPwd;
}
