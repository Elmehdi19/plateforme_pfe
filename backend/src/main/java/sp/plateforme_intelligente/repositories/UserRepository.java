package sp.plateforme_intelligente.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import sp.plateforme_intelligente.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
}