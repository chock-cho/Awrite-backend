package Awrite_project.Awrite.service.UserService;

import Awrite_project.Awrite.domain.User;
import Awrite_project.Awrite.web.dto.UserDTO.UserRequestDTO;

import java.util.Optional;

public interface UserQueryService {

    Optional<User> findUser(Long id);
    void resetPassword(String email, UserRequestDTO.PasswordDto request);

    User verifyPasswordResetTokenGet(String token);
    User verifyPasswordResetTokenAndMakeInvalid(String token);
}

