package Awrite_project.Awrite.service;

import Awrite_project.Awrite.apiPayload.code.status.ErrorStatus;
import Awrite_project.Awrite.apiPayload.exception.TempHandler;
import Awrite_project.Awrite.domain.Diary;
import Awrite_project.Awrite.domain.Heart;
import Awrite_project.Awrite.domain.User;
import Awrite_project.Awrite.repository.DiaryRepository;
import Awrite_project.Awrite.repository.HeartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class HeartService {

    // 모든 좋아요 일기 리스트 조회

    // 좋아요 일기 상세 조회

    // 좋아요 일기 좋아요 취소

}
