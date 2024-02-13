package Awrite_project.Awrite.service;

import Awrite_project.Awrite.domain.User;
import Awrite_project.Awrite.repository.UserRepository;
import Awrite_project.Awrite.service.UserService.UserQueryService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PasswordResetService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private final UserQueryService userQueryService;

    private String generateResetToken() {
        // 임의의 토큰 생성 로직 (예: UUID 사용)
        return UUID.randomUUID().toString();
    }

    @Transactional
    public void requestPasswordReset(String userEmail) throws MessagingException, UnsupportedEncodingException {
        User user = userRepository.findByEmail(userEmail);

        if(user != null){
            // 임의의 토큰 생성 및 설정
            String resetToken = generateResetToken();
            user.setResetToken(resetToken);
            user.setTokenExpiry(LocalDateTime.now().plusHours(24));

            userRepository.save(user);

            // 이메일로 비번 재설정 링크 전송
            sendResetLinkEmail(user.getEmail(), resetToken);
        }
        else {
            throw new RuntimeException("유효하지 않은 이메일 주소입니다.");
        }
    }

    private void sendResetLinkEmail(String userEmail, String resetToken) throws MessagingException {
        String resetLink = "http://localhost:8080/myPage/users/password-reset?token="+resetToken;
        // 로컬호스트라서 resetLink가 이렇게 발급됩니다.
        // 이제 userQueryService를 사용할 수 있습니다.
        User user = userQueryService.verifyPasswordResetTokenGet(resetToken);
        emailService.sendResetEmail(userEmail, resetLink);
    }
}
