package Awrite_project.Awrite.web.controller;

import Awrite_project.Awrite.domain.User;
import Awrite_project.Awrite.service.UserService.UserLoginService;
import Awrite_project.Awrite.web.SessionConst;
import Awrite_project.Awrite.web.dto.LoginUserRequest;
import Awrite_project.Awrite.web.dto.LoginUserResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserLoginController {

    private final UserLoginService userloginService;

    @PostMapping("/users/login")
    public LoginUserResponse login(
            @RequestBody @Valid LoginUserRequest param,
            HttpServletResponse response,
            HttpServletRequest request
            ) {

        User loginUser = userloginService.login(param.getLoginEmail(), param.getLoginPwd());

        if(loginUser == null){
            return new LoginUserResponse(-1, "존재하지 않는 회원이거나 비밀번호가 일치하지 않습니다.");
        }
        else {
            // 로그인 성공 처리
            // 세션이 있다면 세션을 반환해주고, 아니면 신규 세션을 생성해준다.
            HttpSession session = request.getSession();
            // 세션에 회원 로그인 정보 보관
            session.setAttribute(SessionConst.LOGIN_USER, loginUser);

            return new LoginUserResponse(1, "로그인 성공");
        }
    }

    @PostMapping("/users/logout")
    public LoginUserResponse logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate(); // 세션 정보가 있으면 정보 삭제
        }
        return new LoginUserResponse(1, "로그아웃 성공");
    }

}
