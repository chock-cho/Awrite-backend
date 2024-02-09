package Awrite_project.Awrite.repository;

import Awrite_project.Awrite.domain.Diary;
import Awrite_project.Awrite.domain.Heart;
import Awrite_project.Awrite.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeartRepository extends JpaRepository<Heart, Long> {
    void deleteByUserAndDiary(User user, Diary diary);
}