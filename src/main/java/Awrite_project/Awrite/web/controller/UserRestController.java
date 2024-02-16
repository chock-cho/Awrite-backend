package Awrite_project.Awrite.web.controller;

import Awrite_project.Awrite.apiPayload.exception.DuplicateEmailException;
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
        try {
            User member = userCommandService.joinUser(request);
            return ApiResponse.onSuccess(UserConverter.toJoinResultDTO(member));
        } catch(DuplicateEmailException e){
            return ApiResponse.onFailure("DUPLICATE_EMAIL", "이미 가입된 이메일입니다.", null);
        } catch(Exception e){
            return ApiResponse.onFailure("UNKNOWN_ERROR", "알 수 없는 오류가 발생했습니다.", null);
        }

    }

    @PostMapping("/join/email-check")
    public ApiResponse<String> sendVerificationCode(
        @RequestBody @Valid UserRequestDTO.EmailDto request) throws MessagingException {
        try {
            String verificationCode = userCommandService.sendVerificationCode(request.getEmail());
            return ApiResponse.onSuccess("해당 이메일로 인증 코드를 전송하였습니다:" +verificationCode);
        } catch (MessagingException e){
            return ApiResponse.onFailure("EMAIL_SEND_FAILURE", "인증 코드 발송 실패", null);
        }
    }

    @PostMapping("/join/code-verification")
    public ApiResponse<String> codeVerification(
            @RequestBody @Valid UserRequestDTO.CodeVerificationDto request){
        try {
            userCommandService.completeRegisteration(request);
            return ApiResponse.onSuccess("이메일 인증에 성공하였습니다. 회원가입이 성공적으로 완료되었습니다.");
        } catch(Exception e){
            return ApiResponse.onFailure("CODE_VERIFICATION_FAILURE", "코드 인증에 실패하여 회원가입에 실패하였습니다.", null);
        }
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
    public ApiResponse<String> logout(HttpServletRequest request) {
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate(); // 세션 무효화
            }
            return ApiResponse.onSuccess("로그아웃 처리 되었습니다");
        } catch (Exception e) {
            return ApiResponse.onFailure("LOGOUT_FAILURE", "로그아웃 처리 중에 오류가 발생했습니다.", null);
        }
    }

}