package Awrite_project.Awrite.web.controller;

import Awrite_project.Awrite.apiPayload.ApiResponse;
import Awrite_project.Awrite.domain.User;
import Awrite_project.Awrite.service.PasswordResetService;
import Awrite_project.Awrite.service.UserService.UserQueryService;
import Awrite_project.Awrite.web.dto.UserDTO.UserRequestDTO;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/myPage/users/password-reset")
public class PasswordResetController {

    @Autowired
    private PasswordResetService passwordResetService;
    @Autowired
    private UserQueryService userQueryService;

    /* 비밀번호 재설정 링크 이메일 전송 */
    @PostMapping("/mail")
    public ResponseEntity<String> requestEmailToReset(
            @RequestBody @Valid UserRequestDTO.EmailDto request){
        try {
            passwordResetService.requestPasswordReset(request.getEmail());
            return ResponseEntity.ok("메일 발송 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /* 비밀번호 재설정 페이지 */
    @GetMapping("/password-reset")
    public ResponseEntity<String> getPasswordResetToken(@RequestParam String token) {
        // 토큰을 이용하여 사용자 확인
        User user = userQueryService.verifyPasswordResetTokenGet(token);

        if (user != null) {
            // 사용자가 존재하면 토큰을 JSON 응답으로 전송
            return ResponseEntity.ok("{\"token\": \"" + token + "\"}");
        } else {
            // 토큰이 유효하지 않은 경우 다른 처리를 수행하거나 오류 응답
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid token");
        }
    }

    @PostMapping("")
    public ApiResponse<String> resetPassword(
            @RequestParam String token, @RequestBody @Valid UserRequestDTO.PasswordDto request,
            HttpSession session){
        User user = userQueryService.verifyPasswordResetTokenAndMakeInvalid(token);
        if(user != null) {
            userQueryService.resetPassword(user.getEmail(), request);
            updateSessionPassword(session, request.getNewPassword());

            return ApiResponse.onSuccess("”비밀번호가 성공적으로 재설정되었습니다.");
        }
        return ApiResponse.onFailure("500", "비밀번호 재설정에 실패하였습니다.", null);
    }

    // 세션 처리
    private void updateSessionPassword(HttpSession session, String newPassword){
        // 세션에서 사용자 정보를 가져오고 비밀번호 업데이트
        User currentUser = (User)session.getAttribute("user");
        if(currentUser != null) {
            currentUser.setPassword(newPassword);
            session.setAttribute("user", currentUser);
        }
    }
}
