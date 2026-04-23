package sp.plateforme_intelligente.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import sp.plateforme_intelligente.models.Absence;

public interface AbsenceRepository extends JpaRepository<Absence, Long> {
    List<Absence> findByEtudiantId(Long etudiantId);
    List<Absence> findByModuleId(Long moduleId);
    long countByEtudiantIdAndJustifieeFalse(Long etudiantId);
}