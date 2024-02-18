package Awrite_project.Awrite.web.controller;

import Awrite_project.Awrite.apiPayload.ApiResponse;
import Awrite_project.Awrite.domain.Diary;
import Awrite_project.Awrite.domain.User;
import Awrite_project.Awrite.service.DiaryService;
import Awrite_project.Awrite.web.dto.DiaryDTO.DiaryRequestDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.logging.Logger;
@RestController
@RequestMapping("/diary")
public class DiaryController {
    private static final Logger logger = Logger.getLogger(DiaryController.class.getName());
    private final DiaryService diaryService;

    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    // 일기 글 등록
    @PostMapping("/write")
    public ApiResponse<String> saveDiary(
                                             @RequestParam("date") LocalDate date,
                                             @RequestParam("imgUrl") MultipartFile imgUrl,
                                             @RequestParam("title") String title,
                                             @RequestParam("content") String content,
                                             @RequestParam("secret") boolean secret,
                                             @RequestParam("theme") int theme
                                                 ,HttpServletRequest request) {
        try {
            // 세션에서 사용자 ID 가져오기
            // Diary 객체 생성 및 처리
            DiaryRequestDTO diaryRequestDTO = new DiaryRequestDTO();
            diaryRequestDTO.setDate(date);
            diaryRequestDTO.setImgUrl(imgUrl);
            diaryRequestDTO.setTitle(title);
            diaryRequestDTO.setContent(content);
            diaryRequestDTO.setSecret(secret);
            diaryRequestDTO.setTheme(theme);

            Long currentUserId = (Long)request.getSession().getAttribute("userId");
            // 세션에 저장된 userId 로그로 출력
            diaryService.join(diaryRequestDTO, currentUserId);
            return ApiResponse.onSuccess("일기 등록에 성공하였습니다.");
        } catch(Exception e){
            return ApiResponse.onFailure("DIARY_SAVE_FAILURE", "일기 등록 중에 오류가 발생하였습니다.", null);
        }
    }

    // 일기 글 삭제
    @DeleteMapping("/delete/{diaryId}")
    public ApiResponse<String> deleteDiary(@PathVariable Long diaryId, HttpServletRequest request) {
        try {
            // 세션에서 사용자 ID 가져오기
            Long currentUserId = (Long) request.getSession().getAttribute("userId");
            diaryService.deleteDiary(diaryId, currentUserId);
            return ApiResponse.onSuccess("일기 삭제에 성공하였습니다.");
        } catch (Exception e) {
            return ApiResponse.onFailure("DIARY_DELETE_FAILURE", "일기 삭제 중에 오류가 발생했습니다.", null);
        }
    }
}

