package vntu.fcsa.gonchar.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import vntu.fcsa.gonchar.entities.User;

import java.util.Optional;

@Component
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String userNnme);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
