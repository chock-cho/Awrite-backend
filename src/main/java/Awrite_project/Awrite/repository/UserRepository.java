package Awrite_project.Awrite.repository;

import Awrite_project.Awrite.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
