package Awrite_project.Awrite.service.UserService;

import Awrite_project.Awrite.web.dto.UserDTO.UserRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import Awrite_project.Awrite.domain.User;
import Awrite_project.Awrite.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserQueryServiceImpl implements UserQueryService{

    private final UserRepository userRepository;

    @Override
    public Optional<User> findUser(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    @Override
    public void resetPassword(String email, UserRequestDTO.PasswordDto request) {
        User user = userRepository.findByEmail(email);
        user.setPassword(request.getNewPassword());
    }

    public User verifyPasswordResetTokenGet(String token) {
        // 토큰이 null이거나 빈 문자열인 경우
        if (token == null || token.isEmpty()) {
            return null;
        }

        // 토큰을 사용하여 사용자를 찾음
        User user = userRepository.findByResetToken(token);

        // 사용자가 존재하고, 토큰의 만료 기간 등을 확인할 수 있는 로직 추가
        if (user != null && isTokenValid(user.getTokenExpiry())) {
            // 토큰이 유효한 경우
            userRepository.save(user);

            return user;
        }

        // 토큰이 유효하지 않은 경우
        System.out.println("유효하지 않은 토큰: "+token);
        return null;
    }

    public User verifyPasswordResetTokenAndMakeInvalid(String token) {
        // 토큰이 null이거나 빈 문자열인 경우
        if (token == null || token.isEmpty()) {
            return null;
        }

        // 토큰을 사용하여 사용자를 찾음
        User user = userRepository.findByResetToken(token);

        // 사용자가 존재하고, 토큰의 만료 기간 등을 확인할 수 있는 로직 추가
        if (user != null && isTokenValid(user.getTokenExpiry())) {
            // 토큰이 유효한 경우
            user.setResetToken(null);
            user.setTokenExpiry(null);
            userRepository.save(user);

            return user;
        }

        // 토큰이 유효하지 않은 경우
        System.out.println("유효하지 않은 토큰: "+token);
        return null;
    }




    private boolean isTokenValid(LocalDateTime expiryDateTime) {
        // 만료 일자와 현재 시간을 비교하여 토큰이 유효한지 확인하는 로직
        // 예를 들어, expiryDateTime이 현재 시간보다 이후인지 확인
        return expiryDateTime != null && expiryDateTime.isAfter(LocalDateTime.now());
    }


}