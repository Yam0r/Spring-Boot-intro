package mate.academy.springintro.repository;

import java.util.Optional;
import mate.academy.springintro.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
