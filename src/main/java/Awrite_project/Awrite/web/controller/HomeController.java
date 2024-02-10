package Awrite_project.Awrite.web.controller;

import Awrite_project.Awrite.repository.DiaryRepository;
import Awrite_project.Awrite.service.DiaryService;
import Awrite_project.Awrite.service.HomeService;
import Awrite_project.Awrite.web.dto.DiaryDTO.DiaryListResponseDTO;
import Awrite_project.Awrite.web.dto.DiaryDTO.DiaryResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Digits;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {

    private final HomeService homeService;
    private final DiaryRepository diaryRepository;


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
}
