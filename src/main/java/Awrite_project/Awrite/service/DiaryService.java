package Awrite_project.Awrite.service;

import Awrite_project.Awrite.domain.Diary;
import Awrite_project.Awrite.repository.DiaryRepository;
import Awrite_project.Awrite.web.dto.DiaryResponseDTO;
import Awrite_project.Awrite.web.dto.DiaryUpdateRequestDTO;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiaryService {

    private final DiaryRepository diaryRepository;

    public void saveDiary(Diary diary) {
        diaryRepository.save(diary);
    }

    public boolean deleteDiaryByIdandUserId(Long diaryId, Long userId) {
        // 해당 ID의 일기를 찾아본다.
        Diary diary = diaryRepository.findById(diaryId).orElse(null);

        // 일기가 존재하고, 일기를 작성한 사용자와 요청한 사용자가 일치하면 삭제한다
        if (diary != null && diary.getUser().getId().equals(userId)) {
            diaryRepository.delete(diary);
            return true;
        }
        return false;
    }

    public boolean updateDiary(Long diaryId, Long userId, DiaryUpdateRequestDTO diaryUpdateRequestDTO) {
        // 해당 id의 일기를 찾아봅니다.
        Diary existingDiary = diaryRepository.findById(diaryId).orElse(null);

        // 일기가 존재하고, 일기를 작성한 사용자와 수정을 요청한 사용자가 일치하면 수정합니다.
        if (existingDiary != null && existingDiary.getUser().getId().equals(userId)) {
            // request body에 실려온 필드만 업데이트
            if (diaryUpdateRequestDTO.getTitle() != null) {
                existingDiary.setTitle(diaryUpdateRequestDTO.getContent());
            }
            if (diaryUpdateRequestDTO.getContent() != null) {
                existingDiary.setContent(diaryUpdateRequestDTO.getContent());
            }
            if (diaryUpdateRequestDTO.getTheme() != null) {
                existingDiary.setTheme(diaryUpdateRequestDTO.getTheme());
            }
            existingDiary.setSecret(diaryUpdateRequestDTO.isSecret());

            diaryRepository.save(existingDiary);
            return true;
        }
        return false;
    }
}
