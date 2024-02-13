package Awrite_project.Awrite.service;

import Awrite_project.Awrite.domain.User;
import Awrite_project.Awrite.repository.DiaryRepository;
import Awrite_project.Awrite.repository.UserRepository;
import Awrite_project.Awrite.web.dto.MyPageDTO.MyPageNickNameRequestDTO;
import Awrite_project.Awrite.web.dto.MyPageDTO.MyPageProfileRequestDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import Awrite_project.Awrite.web.dto.MyPageDTO.MyPageResponseDTO;
import java.util.NoSuchElementException;

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
    public void checkPasswordAndWithDraw(Long userId, String password){
        // 사용자 정보 조회
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new NoSuchElementException("사용자를 찾을 수 없습니다."));

        // 비밀번호 일치 여부 확인
        if(!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        // 회원 탈퇴 전, 연결된 일기 삭제
        diaryRepository.deleteByAuthorId(userId);
        // 회원 탈퇴 -> 사용자 삭제
        userRepository.delete(user);
    }

    // 좋아요 많은 순 5개 일기 조회

    // 좋아요 많은 순 5개 일기 상세 조회

    // 날짜별 일기 상세 조회

    // 상세 조회 시 일기 삭제

}
