package Awrite_project.Awrite.web.controller;

import Awrite_project.Awrite.domain.Heart;
import Awrite_project.Awrite.domain.User;
import Awrite_project.Awrite.repository.DiaryRepository;
import Awrite_project.Awrite.service.DiaryService;
import Awrite_project.Awrite.service.HeartService;
import Awrite_project.Awrite.service.HomeService;
import Awrite_project.Awrite.web.dto.DiaryDTO.DiaryListResponseDTO;
import Awrite_project.Awrite.web.dto.DiaryDTO.DiaryResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Digits;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {

    private final HomeService homeService;
    private final DiaryRepository diaryRepository;
    private final HeartService heartService;

    // 일기 목록 보기
    @GetMapping("")
    public ResponseEntity<List<DiaryListResponseDTO>> getDiaryList(HttpServletRequest request){
        try {
            // 세션에서 사용자 ID 가져오기
            Long currentUserId = (Long)request.getSession().getAttribute("userId");
            // 세션에 저장된 userId를 이용하여 일기 목록 조회 서비스 호출
            List<DiaryListResponseDTO> diaryList = homeService.getDiaryList(currentUserId);

            // 조회된 일기 목록을 반환
            return ResponseEntity.ok(diaryList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //일기 하나 상세 조회
    @GetMapping("/{diaryId}")
    public ResponseEntity<DiaryResponseDTO> getOneDiary(@PathVariable Long diaryId, HttpServletRequest request) {
        try {
            // 세션에서 사용자 ID 가져오기
            Long currentUserId = (Long)request.getSession().getAttribute("userId");
            // 세션에 저장된 userId를 이용하여 일기 목록 조회 서비스 호출
            DiaryResponseDTO diaryResponseDTO = homeService.clickDiary(diaryId, currentUserId);

            // 조회된 일기 상세 정보를 반환
            return ResponseEntity.ok(diaryResponseDTO);
        }
        catch (Exception e) {
            // 그 외 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 좋아요 등록
    @PostMapping("/hearts/{diaryId}/{userId}")
    // @PostMapping("/diary/{diaryId}/heart")
    public ResponseEntity<Void> addHeart(User user, @PathVariable Long diaryId) {
        Heart heart = homeService.addHeart(user, diaryId);
//        return ResponseEntity
//                .created(URI.create("/diary/" + diaryId + "/heart/" + heart.getId()))
//                .created(URI.create("/diary/" + diaryId + "/heart/" + heart.getId()))
//                .build();
        return ResponseEntity.ok().build();
    }

    // 좋아요 취소
    @DeleteMapping("/hearts/{diaryId}/{userId}")
//    @DeleteMapping("/diary/{diaryId}/heart")
    public ResponseEntity<Void> deleteHeart(User user, @PathVariable Long diaryId) {
        homeService.deleteHeart(user, diaryId);
        return ResponseEntity.noContent().build();
    }
}
