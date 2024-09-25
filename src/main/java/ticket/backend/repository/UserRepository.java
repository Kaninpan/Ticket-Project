package ticket.backend.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ticket.backend.entity.UserEntity;

import java.time.LocalDateTime;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
    UserEntity findByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE UserEntity u SET u.lastLogin = :lastLogin WHERE u.username = :username")
    void updateLastLogin(@Param("username") String username, @Param("lastLogin") LocalDateTime lastLogin);
}
