package sp.plateforme_intelligente.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sp.plateforme_intelligente.models.Professeur;
import sp.plateforme_intelligente.models.User;

public interface ProfesseurRepository extends JpaRepository<Professeur, Long> {

    Optional<Professeur> findByUserId(Long userId);
    Optional<Professeur> findByUser(User user);
    @Query("SELECT p FROM Professeur p WHERE p.user.nom LIKE %:keyword% OR p.specialite LIKE %:keyword%")
    List<Professeur> rechercher(@Param("keyword") String keyword);
    List<Professeur> findByResponsableFiliereTrue();
}