package Awrite_project.Awrite.service;

import Awrite_project.Awrite.apiPayload.code.status.ErrorStatus;
import Awrite_project.Awrite.apiPayload.exception.TempHandler;
import Awrite_project.Awrite.domain.Diary;
import Awrite_project.Awrite.domain.Heart;
import Awrite_project.Awrite.domain.User;
import Awrite_project.Awrite.repository.DiaryRepository;
import Awrite_project.Awrite.repository.HeartRepository;
import Awrite_project.Awrite.repository.UserRepository;
import Awrite_project.Awrite.web.dto.DiaryDTO.DiaryListResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class HeartService {

    private final HeartRepository heartRepository;
    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;

    @Transactional
    // 모든 좋아요 일기 리스트 조회
    public List<DiaryListResponseDTO> getHeartDiaryList(Long currentUserId) {
        try {
            // 사용자가 좋아요를 누른 Diary 목록 가져오기
            List<Diary> heartDiaryList = diaryRepository.findDiariesByHeartsUserId(currentUserId);

            // DiaryListResponseDTO로 변환
            List<DiaryListResponseDTO> heartDiaryResponseList = heartDiaryList
                    .stream()
                    .map(diary -> {
                        boolean heartby = diaryRepository.isHeartByUser(diary.getId(), currentUserId);
                        return new DiaryListResponseDTO(diary, heartby);
                    })
                    .collect(Collectors.toList());

            return heartDiaryResponseList;
        } catch (Exception e) {
            // 예외 처리
            return Collections.emptyList();
        }
    }

    // 좋아요 등록
    public Heart addHeart(Long userId, Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new TempHandler(ErrorStatus.ARTICLE_NOT_FOUND));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new TempHandler(ErrorStatus.MEMBER_NOT_FOUND));

        Heart heart = new Heart(user, diary);

        return heartRepository.save(heart);
    }

    @Transactional
    // 좋아요 취소
    public void deleteHeart(Long userId, Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new TempHandler(ErrorStatus.ARTICLE_NOT_FOUND));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new TempHandler(ErrorStatus.MEMBER_NOT_FOUND));

        heartRepository.deleteByUserAndDiary(user, diary);
    }

}