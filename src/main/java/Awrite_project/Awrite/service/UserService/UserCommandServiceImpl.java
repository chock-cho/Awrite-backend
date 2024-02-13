package Awrite_project.Awrite.service.UserService;

import Awrite_project.Awrite.domain.User;
import Awrite_project.Awrite.service.EmailService;
import Awrite_project.Awrite.web.dto.UserDTO.UserRequestDTO;
import Awrite_project.Awrite.repository.UserRepository;
import Awrite_project.Awrite.converter.UserConverter;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;
    @Autowired
    private EmailService emailService;

    @Override
    @Transactional
    public User joinUser(UserRequestDTO.JoinDto request) {

        User newUser = UserConverter.toUser(request);
        newUser.setPassword(newUser.getPassword());
        newUser.setVerified(false);

        return userRepository.save(newUser);
    }

    private String generateVerificationCode(){
        // 4자리 랜덤 인증 코드 생성
        return String.format("%04d", new Random().nextInt(10000));
    }

    @Override
    @Transactional
    public void sendVerificationCode(String email) throws MessagingException {
        String verificationCode = generateVerificationCode();
        emailService.sendEmail(email, verificationCode);

        User user = userRepository.findByEmail(email);
        user.setVerificationCode(verificationCode);
        userRepository.save(user);
    }

    @Override
    public void completeRegisteration(UserRequestDTO.CodeVerificationDto request) {
        User user = userRepository.findByEmail(request.getEmail());

        if(!user.isVerified() && Objects.equals(request.getCode(), user.getVerificationCode())) {
            user.setVerified(true);
            userRepository.save(user);
        }
        else {
            throw new RuntimeException("이메일 인증에 실패했습니다.");
        }
    }


}