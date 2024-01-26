package Awrite_project.Awrite.web.controller;

import Awrite_project.Awrite.domain.Diary;
import Awrite_project.Awrite.service.DiaryService;
import Awrite_project.Awrite.web.dto.DiaryUpdateRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class DiaryApiController {

    private final DiaryService diaryService;
    // 회원 한 명의 일기 목록을 응답
    // @param id := 해당 회원의 일기 전체 목록

    @PostMapping("/diary/write")
    public String writeDiary(Diary diary){
        diaryService.saveDiary(diary);
        return "redirect:/home"; // 일기 목록 페이지로 리다이렉트
    }

    @DeleteMapping("/diary/{diaryId}/{userId}")
    public ResponseEntity<String> deleteDiary(@PathVariable Long diaryId, @PathVariable Long userId){
        boolean isDeleted = diaryService.deleteDiaryByIdandUserId(diaryId, userId);
        if(isDeleted){
            return ResponseEntity.ok("성공적으로 일기가 삭제되었습니다.");
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/diary/{diaryId}/{userId}")
    public ResponseEntity<String> updateDiary(
            @PathVariable Long diaryId,
            @PathVariable Long userId,
            @RequestBody DiaryUpdateRequestDTO diaryUpdateRequestDTO) {
        boolean isUpdated = diaryService.updateDiary(diaryId, userId, diaryUpdateRequestDTO);

        if(isUpdated){
            return ResponseEntity.ok("성공적으로 수정사항이 반영되었습니다.");
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }
}
