package Awrite_project.Awrite.service;

import Awrite_project.Awrite.domain.Diary;
import Awrite_project.Awrite.domain.User;
import Awrite_project.Awrite.domain.Like;
import Awrite_project.Awrite.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likesRepository;

    // 다른 사용자들의 일기에 좋아요 등록
    public void addLike(User user, Diary diary) {
        Like like = Like.create(); // 생성자 직접 호출 방지
        like.setUser(user);
        like.setDiary(diary);
        likesRepository.save(like);
    }
}
