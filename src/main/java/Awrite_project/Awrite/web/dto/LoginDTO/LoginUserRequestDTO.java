package Awrite_project.Awrite.web.dto.LoginDTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginUserRequestDTO {

    @NotNull
    private String email;
    @NotNull
    private String password;
}
