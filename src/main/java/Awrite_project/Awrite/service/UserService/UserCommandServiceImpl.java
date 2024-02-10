package Awrite_project.Awrite.service.UserService;

import Awrite_project.Awrite.domain.User;
import Awrite_project.Awrite.web.dto.JoinDTO.UserRequestDTO;
import Awrite_project.Awrite.repository.UserRepository;
import Awrite_project.Awrite.converter.UserConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public User joinUser(UserRequestDTO.JoinDto request) {


        User newUser = UserConverter.toUser(request);
        newUser.setPassword(newUser.getPassword());
        return userRepository.save(newUser);
    }
}