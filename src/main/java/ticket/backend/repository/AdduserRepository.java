package ticket.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ticket.backend.entity.UserEntity;

public interface AdduserRepository  extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
    UserEntity findByEmail(String email);


}
