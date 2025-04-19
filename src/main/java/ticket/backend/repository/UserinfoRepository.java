package ticket.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ticket.backend.entity.UserEntity;

public interface UserinfoRepository extends JpaRepository<UserEntity, Long> {

}
