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

    private final HeartRepository heartRepository;
    private final DiaryRepository diaryRepository;

    public Heart addHeart(User user, Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new TempHandler(ErrorStatus.ARTICLE_NOT_FOUND));

        Heart heart = new Heart(user, diary);

        return heartRepository.save(heart);

//        try {
//            return heartRepository.save(heart);
//        } catch (DataIntegrityViolationException e) {
//            throw new DuplicateArticleLikeException(member.getId(), articleId);
//        }
    }

    public void deleteHeart(User user, Long diaryId) {
        Diary diary = diaryRepository.findById(diaryId)
                .orElseThrow(() -> new TempHandler(ErrorStatus.ARTICLE_NOT_FOUND));

        heartRepository.deleteByUserAndDiary(user, diary);
    }

}

//@Service
//public class HeartService {
//
//    @Autowired
//    private HeartRepository heartsRepository;
//
//    // 다른 사용자들의 일기에 좋아요 등록
//    public void addHeart(User user, Diary diary) {
//        Heart heart = Heart.create(); // 생성자 직접 호출 방지
//        heart.setUser(user);
//        heart.setDiary(diary);
//        heartsRepository.save(heart);
//    }
//}
