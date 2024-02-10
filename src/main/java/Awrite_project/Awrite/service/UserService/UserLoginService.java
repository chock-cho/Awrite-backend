package Awrite_project.Awrite.service.UserService;

import Awrite_project.Awrite.domain.User;
import Awrite_project.Awrite.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserLoginService {

    private final UserRepository userRepository;

    // loginEmail 중복 체크
    // 중복 되면 true return
    public boolean checkLoginEmailDuplicated(String loginEmail){
        return userRepository.existsByEmail(loginEmail);
    }

    /* 로그인 처리 */
    // @param -> email
    // @param -> password
    // return null -> 로그인 실패

    public User login(String loginEmail, String loginPwd){
        User user = userRepository.findByEmail(loginEmail);

        if(user != null && loginPwd.equals(user.getPassword())){
            // DB에 비밀번호 저장한 값과 로그인 시도하는 패스워드가 같은지 확인하는 로직
            // 비밀번호 일치 -> 회원 객체 반환
            return user;
        }
        else {
            // 해당 이메일에 대한 사용자가 없거나, 비밀번호 불일치
            return null;
        }
    }


}
