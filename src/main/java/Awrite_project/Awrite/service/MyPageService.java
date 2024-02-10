package Awrite_project.Awrite.service;

import Awrite_project.Awrite.domain.User;
import Awrite_project.Awrite.repository.DiaryRepository;
import Awrite_project.Awrite.repository.UserRepository;
import Awrite_project.Awrite.web.dto.MyPageDTO.MyPageNickNameRequestDTO;
import Awrite_project.Awrite.web.dto.MyPageDTO.MyPageProfileRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyPageService {

    private final UserRepository userRepository;
    private final DiaryRepository diaryRepository;

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
}
