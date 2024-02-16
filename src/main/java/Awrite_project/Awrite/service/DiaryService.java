package Awrite_project.Awrite.service;

import Awrite_project.Awrite.config.S3Config;
import Awrite_project.Awrite.domain.Diary;
import Awrite_project.Awrite.domain.User;
import Awrite_project.Awrite.repository.DiaryRepository;
import Awrite_project.Awrite.repository.UserRepository;
import Awrite_project.Awrite.web.dto.DiaryDTO.DiaryRequestDTO;
import Awrite_project.Awrite.web.dto.DiaryDTO.DiaryResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;
    private final S3Config s3Config;

    @Autowired
    public DiaryService(S3Config s3Config, UserRepository userRepository, DiaryRepository diaryRepository) {
        this.s3Config = s3Config;
        this.userRepository = userRepository;
        this.diaryRepository = diaryRepository;
        System.out.println("DiaryService created with S3Config: " + s3Config);
    }

    // 일기 등록
    @Transactional
    public void join(DiaryRequestDTO diaryRequestDTO, Long currentUserId) {
        try {
            if(diaryRequestDTO.getImgUrl() != null){
                String filePath = s3Config.upload(diaryRequestDTO.getImgUrl());
                diaryRequestDTO.toEntity().setImgUrl(filePath);
            }
            // 사용자 ID를 이용하여 사용자 정보를 조회하고, 해당 사용자를 일기 작성자로 설정
            User currentUser = userRepository.findById(currentUserId)
                    .orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다."));

            // DiaryRequestDTO에 사용자 정보 설정
            diaryRequestDTO.setAuthor(currentUser);

            Diary diary = diaryRequestDTO.toEntity();
            diaryRepository.save(diary);
        }
        catch(IOException e){
            throw new RuntimeException(e);
        }
    }



    @Transactional
    // 일기 삭제
    public void deleteDiary(Long diaryId, Long currentUserId){


        // 삭제할 일기가 존재하는지 확인
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new NoSuchElementException("삭제할 일기가 존재하지 않습니다."));

        // 일기 작성자 아이디 가져오기
        Long diaryAuthorId = diary.getAuthor().getId();
        if (currentUserId.equals(diaryAuthorId)) {
            diaryRepository.deleteById(diaryId);
        } else {
            throw new AccessDeniedException("일기 삭제 권한이 없습니다.");
        }
    }

    // 일기 하나 상세 조회
    public DiaryResponseDTO clickDiary(Long diaryId){
        // 조회할 일기가 존재하는지 확인
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(()-> new NoSuchElementException(("조회할 일기가 존재하지 않습니다")));

        // 일기 엔티티를 DTO로 변환하여 반환
        return new DiaryResponseDTO(diary);
    }
}
