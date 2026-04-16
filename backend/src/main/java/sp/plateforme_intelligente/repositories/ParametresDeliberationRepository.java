package sp.plateforme_intelligente.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import sp.plateforme_intelligente.models.ParametresDeliberation;

public interface ParametresDeliberationRepository extends JpaRepository<ParametresDeliberation, Long> {
    
    Optional<ParametresDeliberation> findByFiliereIdAndActifTrue(Long filiereId);
    
    Optional<ParametresDeliberation> findByFiliereId(Long filiereId);
}