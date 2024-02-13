package Awrite_project.Awrite.web.controller;

import Awrite_project.Awrite.service.MyPageService;
import Awrite_project.Awrite.web.dto.MyPageDTO.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/myPage/users")
@RequiredArgsConstructor
public class MyPageController {
    private final MyPageService myPageService;

    // 마이페이지 회원 정보 조회
    @GetMapping("/info")
    public ResponseEntity<MyPageResponseDTO> getMyProfileInfo(HttpServletRequest request) {
        try {
            // 세션에서 사용자 ID 가져오기
            Long currentUserId = (Long) request.getSession().getAttribute("userId");

            // MyPageService를 통해 사용자의 프로필 정보 조회
            MyPageResponseDTO profileInfo = myPageService.getMyProfileInfo(currentUserId);

            // 조회된 프로필 정보를 ResponseEntity로 반환
            return ResponseEntity.ok(profileInfo);
        } catch (Exception e) {
            // 예외가 발생한 경우 적절한 응답을 반환
            MyPageResponseDTO errorResponse = new MyPageResponseDTO(null, null, null);
            return ResponseEntity.status(500).body(errorResponse);        }
    }

    // 프로필 사진 변경
    @PatchMapping("/profile-image")
    public ResponseEntity<String> updateProfileImage(@RequestBody @Valid MyPageProfileRequestDTO requestDTO,
                                                     HttpServletRequest request){
        try{
            // 세션에서 사용자 ID 가져오기
            Long currentUserId = (Long)request.getSession().getAttribute("userId");
            myPageService.updateMyProfilePicture(currentUserId, requestDTO);
            return ResponseEntity.ok("프로필 이미지가 성공적으로 변경되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(500).body("프로필 이미지 변경에 실패했습니다.");
        }
    }

    // 닉네임 변경
    @PatchMapping("/nickname")
    public ResponseEntity<String> updateMyNickname(@RequestBody @Valid MyPageNickNameRequestDTO requestDTO,
                                                   HttpServletRequest request){
        try{
            // 세션에서 사용자 ID 가져오기
            Long currentUserId = (Long)request.getSession().getAttribute("userId");
            myPageService.updateMyNickname(currentUserId, requestDTO);
            return ResponseEntity.ok("닉네임이 성공적으로 변경되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(500).body("닉네임 변경에 실패했습니다.");
        }
    }

    // 회원 탈퇴하기
    @PostMapping("/withdrawal")
    public ResponseEntity<MyPageWithdrawalResponseDTO> checkPasswordAndWithDraw(
            @RequestBody MyPageWithdrawalRequestDTO withdrawalRequestDTO,
            HttpServletRequest request) {
        try {
            // 세션에서 사용자 ID 가져오기
            Long currentUserId = (Long) request.getSession().getAttribute("userId");
            myPageService.checkPasswordAndWithDraw(currentUserId, withdrawalRequestDTO.getPassword());
            return ResponseEntity.ok(new MyPageWithdrawalResponseDTO("회원 탈퇴가 성공적으로 처리되었습니다."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MyPageWithdrawalResponseDTO("회원 탈퇴 처리에 실패했습니다."));
        }
    }

    // 좋아요 많은 순 5개 일기 조회

    // 좋아요 많은 순 5개 일기 상세 조회

    // 날짜별 일기 상세 조회

    // 상세 조회 시 일기 삭제

}
