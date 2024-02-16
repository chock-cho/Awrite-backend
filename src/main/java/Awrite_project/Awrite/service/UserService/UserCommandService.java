package Awrite_project.Awrite.service.UserService;

import Awrite_project.Awrite.domain.User;
import Awrite_project.Awrite.web.dto.UserDTO.UserRequestDTO;
import jakarta.mail.MessagingException;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public interface UserCommandService {

    User joinUser(UserRequestDTO.JoinDto request);

    String sendVerificationCode(@NotNull @NotBlank @Email String request) throws MessagingException;
    void completeRegisteration(UserRequestDTO.CodeVerificationDto request);
}