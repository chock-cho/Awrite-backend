package Awrite_project.Awrite.web.controller;

import Awrite_project.Awrite.service.UserService.UserLoginService;
import Awrite_project.Awrite.web.dto.LoginDTO.LoginUserRequestDTO;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import Awrite_project.Awrite.apiPayload.ApiResponse;
import Awrite_project.Awrite.converter.UserConverter;
import Awrite_project.Awrite.domain.User;
import Awrite_project.Awrite.service.UserService.UserCommandService;
import Awrite_project.Awrite.web.dto.UserDTO.UserRequestDTO;
import Awrite_project.Awrite.web.dto.UserDTO.UserResponseDTO;

import jakarta.validation.Valid;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/users")
public class UserRestController {

    private final UserCommandService userCommandService;
    private final UserLoginService userLoginService;

    @PostMapping("/join")
    public ApiResponse<UserResponseDTO.JoinResultDTO> join(@RequestBody @Valid UserRequestDTO.JoinDto request){
        User member = userCommandService.joinUser(request);
        return ApiResponse.onSuccess(UserConverter.toJoinResultDTO(member));
    }

    @PostMapping("/join/email-check")
    public ApiResponse<String> sendVerificationCode(
        @RequestBody @Valid UserRequestDTO.EmailDto request) throws MessagingException {
        userCommandService.sendVerificationCode(request.getEmail());
        return ApiResponse.onSuccess("해당 이메일로 인증 코드를 전송하였습니다.");
    }

    @PostMapping("/join/code-verification")
    public ApiResponse<String> codeVerification(
            @RequestBody @Valid UserRequestDTO.CodeVerificationDto request){
        userCommandService.completeRegisteration(request);
        return ApiResponse.onSuccess("회원가입이 성공적으로 완료되었습니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody @Valid LoginUserRequestDTO request, HttpSession session){
        User user = userLoginService.login(request.getEmail(), request.getPassword());

        if(user != null){
            session.setAttribute("userId", user.getId()); // 세션에 사용자 ID 저장
            return ResponseEntity.ok("로그인에 성공하였습니다.");
        }
        else {
            // 로그인 실패 시에도 세션에 사용자 ID 저장 (이 부분을 수정)
            session.setAttribute("userId", null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인에 실패하였습니다.");
        }
    }


    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // 세션 무효화
        }
        return "redirect:/login?logout"; // 로그아웃 후 리다이렉트할 페이지
    }

}