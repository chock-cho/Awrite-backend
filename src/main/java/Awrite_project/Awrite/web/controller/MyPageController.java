package Awrite_project.Awrite.web.controller;

import Awrite_project.Awrite.service.DiaryService;
import Awrite_project.Awrite.service.HeartService;
import Awrite_project.Awrite.service.HomeService;
import Awrite_project.Awrite.service.MyPageService;
import Awrite_project.Awrite.web.dto.DiaryDTO.DiaryListResponseDTO;
import Awrite_project.Awrite.web.dto.DiaryDTO.DiaryResponseDTO;
import Awrite_project.Awrite.web.dto.MyPageDTO.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/myPage/users")
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;
    private final DiaryService diaryService;
    private final HomeService homeService;
    private final HeartService heartService;

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
    @GetMapping("/heart")
    public ResponseEntity<List<MyPageHeartListResponseDTO>> getHeartDiaryList(HttpServletRequest request){
        try {
            // 세션에서 사용자 ID 가져오기
            Long currentUserId = (Long)request.getSession().getAttribute("userId");
            // 세션에 저장된 userId를 이용하여 일기 목록 조회 서비스 호출
            List<MyPageHeartListResponseDTO> diaryList = myPageService.getHeartTop5List(currentUserId);

            // 조회된 일기 목록을 반환
            return ResponseEntity.ok(diaryList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 좋아요 많은 순 5개 일기 상세 조회
    @GetMapping("/heart/{diaryId}")
    public ResponseEntity<DiaryResponseDTO> getOneDiary(@PathVariable Long diaryId, HttpServletRequest request) {
        try {
            // diaryId 이용하여 일기 목록 조회 서비스 호출
            DiaryResponseDTO diaryResponseDTO = myPageService.clickDiary(diaryId);

            // 조회된 일기 상세 정보를 반환
            return ResponseEntity.ok(diaryResponseDTO);
        }
        catch (Exception e) {
            // 그 외 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 날짜별 일기 상세 조회
//    매핑은 /myPage/users/{date}
    @GetMapping("/{date}")
    public ResponseEntity<DiaryResponseDTO> getDateDiary(@PathVariable LocalDate date, HttpServletRequest request) {
        try {
            // 세션에서 사용자 ID 가져오기
            Long currentUserId = (Long)request.getSession().getAttribute("userId");
            // 현재 사용자 ID랑 날짜로 일기 조회 서비스 호출
            DiaryResponseDTO diaryResponseDTO = myPageService.clickDate(currentUserId, date);

            // 조회된 일기 상세 정보를 반환
            return ResponseEntity.ok(diaryResponseDTO);
        }
        catch (Exception e) {
            // 그 외 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 상세 조회 시 일기 삭제
    @DeleteMapping("/{date}/{diaryId}")
    public void deleteDiary(@PathVariable Long diaryId, HttpServletRequest request) {
        // 세션에서 사용자 ID 가져오기
        Long currentUserId = (Long)request.getSession().getAttribute("userId");
        diaryService.deleteDiary(diaryId, currentUserId);
    }
}
