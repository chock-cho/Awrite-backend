package Awrite_project.Awrite.service.UserService;

import Awrite_project.Awrite.domain.User;

import java.util.Optional;

public interface UserQueryService {

    Optional<User> findUser(Long id);

}

