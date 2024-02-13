package Awrite_project.Awrite.web.dto.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class UserResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinResultDTO{
        Long userId;
        LocalDateTime createdAt;
        boolean verified;
        String verificationCode;
    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CodeVerificationResultDTO{
        String verificationCode;
        boolean verified;
    }
}
