package Awrite_project.Awrite.repository;

import Awrite_project.Awrite.domain.Diary;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface DiaryRepository extends JpaRepository<Diary, Long> {
    void deleteDiaryByIdandUserId(Long id, Long userId);
}
