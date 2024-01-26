package Awrite_project.Awrite.repository;

import Awrite_project.Awrite.domain.User;
import jakarta.persistence.EntityManager;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByEmail(String email); // email로 user조회
    @NotNull List<User> findAll(); //회원 목록 전체조회

    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);

}
