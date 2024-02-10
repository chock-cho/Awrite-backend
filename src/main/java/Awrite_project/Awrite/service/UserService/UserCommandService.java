package Awrite_project.Awrite.service.UserService;

import Awrite_project.Awrite.domain.User;
import Awrite_project.Awrite.web.dto.JoinDTO.UserRequestDTO;

public interface UserCommandService {

    User joinUser(UserRequestDTO.JoinDto request);
}
