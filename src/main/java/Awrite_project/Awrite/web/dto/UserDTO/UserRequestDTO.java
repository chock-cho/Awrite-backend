package Awrite_project.Awrite.web.dto.UserDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

public class UserRequestDTO {
    @Getter
    public static class JoinDto{
        @NotNull
        @NotBlank(message = "이메일 주소를 입력해주세요.")
        @Email(message = "올바른 이메일 주소를 입력해주세요.")
        private String email;

        @NotNull
        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
        private String password;

        @NotNull
        @NotBlank(message = "닉네임을 입력해주세요.")
        @Size(min = 2, max = 10, message = "닉네임은 2자 이상 10자 이하로 입력해주세요.")
        private String nickname;
    }

    @Getter
    public static class EmailDto{
        @NotNull
        @NotBlank
        @Email(message= "올바른 이메일 주소를 입력해주세요.")
        private String email;
    }

    @Getter
    public static class CodeVerificationDto{
        @NotNull
        @NotBlank
        @Email(message= "올바른 이메일 주소를 입력해주세요.")
        private String email;

        @NotNull
        @NotBlank
        private String code;
    }

    @Getter
    @Setter
    public static class PasswordDto{
        @NotNull
        @NotBlank(message = "새 비밀번호를 입력해주세요.")
        @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
        private String newPassword;

        @NotNull
        @NotBlank(message = "새 비밀번호 확인")
        @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
        private String rewritePassword;
    }
}
