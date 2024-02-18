package Awrite_project.Awrite.service;

import Awrite_project.Awrite.domain.Diary;
import Awrite_project.Awrite.domain.User;
import Awrite_project.Awrite.repository.DiaryRepository;
import Awrite_project.Awrite.repository.UserRepository;
import Awrite_project.Awrite.web.dto.DiaryDTO.DiaryResponseDTO;
import Awrite_project.Awrite.web.dto.MyPageDTO.MyPageHeartListResponseDTO;
import Awrite_project.Awrite.web.dto.MyPageDTO.MyPageNickNameRequestDTO;
import Awrite_project.Awrite.web.dto.MyPageDTO.MyPageProfileRequestDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import Awrite_project.Awrite.web.dto.MyPageDTO.MyPageResponseDTO;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyPageService {

    private final UserRepository userRepository;
    private final DiaryRepository diaryRepository;

    // 프로필 조회
    public MyPageResponseDTO getMyProfileInfo(Long userId) {
        try {
            // 사용자 ID를 기반으로 데이터베이스에서 사용자 정보 조회
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("해당 사용자를 찾을 수 없습니다."));

            // 조회된 사용자 정보를 MyPageResponseDTO로 변환하여 반환
            return new MyPageResponseDTO(user.getProfilePicture(), user.getNickname(), user.getEmail());
        } catch (EntityNotFoundException e) {
            // 사용자를 찾을 수 없는 경우 예외 처리
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다.");
        } catch (Exception e) {
            // 기타 예외 발생 시 예외 처리
            throw new RuntimeException("프로필 정보 조회에 실패했습니다.", e);
        }
    }

    // 닉네임 수정
    @Transactional
    public void updateMyNickname(Long userId, MyPageNickNameRequestDTO requestDTO) {
        // 사용자 정보 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다."));

        // 닉네임 업데이트
        user.setNickname(requestDTO.getNickname());

        userRepository.save(user);
    }

    // 프로필 사진 수정
    @Transactional
    public void updateMyProfilePicture(Long userId, MyPageProfileRequestDTO requestDTO) {
        // 사용자 정보 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        // 프로필 사진 업데이트
        validateProfilePicture(requestDTO.getProfileImg()); // 유효성 검사
        user.setProfilePicture(requestDTO.getProfileImg());

        userRepository.save(user);
    }

    private void validateProfilePicture(Integer profilePicture) {
        if (profilePicture < 1 || profilePicture > 4) {
            throw new IllegalArgumentException("프로필 사진 값은 1~4 사이의 정수여야 합니다.");
        }
    }

    // 회원 탈퇴 처리
    @Transactional
    public void withdraw(Long userId) {
        // 사용자 정보 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다."));

        // 연결된 일기 삭제
        diaryRepository.deleteByAuthorId(userId);

        // 사용자 삭제
        userRepository.delete(user);
    }

    // 비밀번호 일치 여부 확인
    public void checkPassword(Long userId, String password) {
        // 사용자 정보 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다."));

        // 비밀번호 일치 여부 확인
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    // 좋아요 많은순 5개 일기 목록 조회 (사진, 제목, 날짜)
//    내 글 가져오기
//    좋아요 많은 순 정렬 -> 5개 자르기
//    자른 5개 일기에서 사진, 제목, 날짜 리턴하기
    public List<MyPageHeartListResponseDTO> getHeartTop5List(Long currentUserId) {
        try {
            // 사용자와 일치하는 일기 가져오기
            List<MyPageHeartListResponseDTO> userList = diaryRepository.findDiariesByAuthorId(currentUserId)
                    .stream()
                    .map(diary -> {
                        boolean heartby = diaryRepository.isHeartByUser(diary.getId(), currentUserId);
                        return new MyPageHeartListResponseDTO(diary, heartby);
                    })
                    .collect(Collectors.toList());

            // 사용자와 일치하는 일기 중 좋아요 top5 일기 가져오기
            List<MyPageHeartListResponseDTO> heartTop5DiaryList = userList
                    .stream()
                    .sorted(Comparator.comparingLong(MyPageHeartListResponseDTO::getHeartsCount).reversed())
                    .limit(5)
                    .collect(Collectors.toList());

            return heartTop5DiaryList;
        } catch (Exception e) {
            // 예외 처리
            return Collections.emptyList();
        }
    }

    // 좋아요 많은순 일기 5개 상세 조회
    public DiaryResponseDTO clickDiary(Long diaryId){
        // 조회할 일기가 존재하는지 확인
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(()-> new NoSuchElementException(("조회할 일기가 존재하지 않습니다")));

        // 일기 엔티티를 DTO로 변환하여 반환
        return new DiaryResponseDTO(diary);
    }

    // 날짜별 일기 상세 조회
//    내 글 가져오기
//    클릭한 날짜 == 내 글 중 그 날짜에 작성한 글 가져오기
//    상세조회
    public DiaryResponseDTO clickDate(Long currentUserId, LocalDate date) {
        try {
            Diary diary = diaryRepository.findByAuthorIdAndDate(currentUserId, date);

            return new DiaryResponseDTO(diary);
        } catch (NoSuchElementException e) {
            // 조회할 일기가 없는 경우
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "조회할 일기가 존재하지 않습니다", e);
        }
    }
}
