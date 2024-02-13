package Awrite_project.Awrite.service;

import Awrite_project.Awrite.apiPayload.code.status.ErrorStatus;
import Awrite_project.Awrite.apiPayload.exception.TempHandler;
import Awrite_project.Awrite.domain.Diary;
import Awrite_project.Awrite.domain.Heart;
import Awrite_project.Awrite.domain.User;
import Awrite_project.Awrite.repository.DiaryRepository;
import Awrite_project.Awrite.repository.HeartRepository;
import Awrite_project.Awrite.web.dto.DiaryDTO.DiaryListResponseDTO;
import Awrite_project.Awrite.web.dto.DiaryDTO.DiaryResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HomeService {

    private final DiaryRepository diaryRepository;
    private final HeartRepository heartRepository;

    // 모든 일기 리스트 조회
    public List<DiaryListResponseDTO> getDiaryList(Long currentUserId) {
        try {
            // 사용자와 일치하는 일기 가져오기
            List<DiaryListResponseDTO> userDiaryList = diaryRepository.findDiariesByAuthorId(currentUserId)
                    .stream()
                    .map(DiaryListResponseDTO::new)
                    .collect(Collectors.toList());

            // 모든 일기 리스트 중 secret이 아닌 일기 가져오기
            List<DiaryListResponseDTO> nonSecretDiaryList = diaryRepository.findAll()
                    .stream()
                    .filter(diary -> !diary.isSecret())
                    .map(DiaryListResponseDTO::new)
                    .collect(Collectors.toList());

            // 두 리스트를 합침
            List<DiaryListResponseDTO> diaryList = new ArrayList<>();
            diaryList.addAll(userDiaryList);
            diaryList.addAll(nonSecretDiaryList);

            return diaryList;
        } catch (Exception e) {
            // 예외 처리
            return Collections.emptyList();
        }
    }
    // 일기 상세 조회
    public DiaryResponseDTO clickDiary(Long diaryId, Long currentUserId) {
        try {
            // 조회할 일기가 존재하는지 확인
            Diary diary = diaryRepository.findById(diaryId)
                    .orElseThrow(() -> new NoSuchElementException("조회할 일기가 존재하지 않습니다"));

            // 현재 사용자가 해당 일기의 작성자인지 확인
            if (diary.getAuthor().getId().equals(currentUserId)) {
                // 작성자라면 모든 내용 반환
                return new DiaryResponseDTO(diary);
            } else if (!diary.isSecret()) {
                // 작성자가 아니고, 일기가 공개 상태라면 비밀글 여부에 상관없이 반환
                return new DiaryResponseDTO(diary);
            } else {
                // 작성자가 아니고, 일기가 비밀 상태라면 권한이 없음을 반환
                throw new AccessDeniedException("일기 조회 권한이 없습니다.");
            }
        } catch (NoSuchElementException e) {
            // 조회할 일기가 없는 경우
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "조회할 일기가 존재하지 않습니다", e);
        } catch (AccessDeniedException e) {
            // 권한이 없는 경우
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "일기 조회 권한이 없습니다.", e);
        } catch (Exception e) {
            // 그 외 예외 처리
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류", e);
        }
    }
}

