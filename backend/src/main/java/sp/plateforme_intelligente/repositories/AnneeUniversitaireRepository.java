package sp.plateforme_intelligente.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import sp.plateforme_intelligente.models.AnneeUniversitaire;

public interface AnneeUniversitaireRepository extends JpaRepository<AnneeUniversitaire, Long> {
    
    Optional<AnneeUniversitaire> findByCode(String code);
    
    Optional<AnneeUniversitaire> findByActiveTrue();
}