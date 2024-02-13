package Awrite_project.Awrite.repository;

import Awrite_project.Awrite.domain.Diary;
import Awrite_project.Awrite.domain.Heart;
import Awrite_project.Awrite.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface HeartRepository extends JpaRepository<Heart, Long> {
    void deleteByUserAndDiary(User user, Diary diary);

    // 쿼리
//    @Transactional
//    @Modifying
//    @Query("SELECT m FROM table m ORDER BY column DESC LIMIT 10")
//    List<Diary> findDiariesByAuthorId(Long heartsCount);

//    List<Diary> findTop5ByOrderByHeartCountDesc(Long heartsCount); // 좋아요 많은 5개 일기만

//    List<Diary> findAllByOrderByHeartCountDesc(Long heartsCount); // 좋아요 많은순 정렬
}