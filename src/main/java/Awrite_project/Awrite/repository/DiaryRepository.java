package Awrite_project.Awrite.repository;

import Awrite_project.Awrite.domain.Diary;
import Awrite_project.Awrite.web.dto.DiaryDTO.DiaryListResponseDTO;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface DiaryRepository extends JpaRepository<Diary, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Diary d WHERE d.author.id = :userId")
    void deleteByAuthorId(@Param("userId") Long userId);

    List<Diary> findDiariesByAuthorId(Long authorId);

    public Diary findByAuthorIdAndYmlCreatedAt(Long currentUserId, LocalDate ymlCreatedAt);
}
