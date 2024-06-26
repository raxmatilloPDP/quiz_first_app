package uz.pdp.quiz_first_app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.quiz_first_app.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepo extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String username);

    boolean existsByEmail(String username);

    List<User> findAllByIsStartedIsTrueAndIsConnectedIsFalse();
}