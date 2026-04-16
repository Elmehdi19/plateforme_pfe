package sp.plateforme_intelligente.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import sp.plateforme_intelligente.models.Annee;

public interface AnneeRepository extends JpaRepository<Annee, Long> {
    List<Annee> findByFiliereId(Long filiereId);
    List<Annee> findByDiplômanteTrue();
}