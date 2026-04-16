package sp.plateforme_intelligente.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sp.plateforme_intelligente.models.Semestre;

public interface SemestreRepository extends JpaRepository<Semestre, Long> {
    List<Semestre> findByAnneeId(Long anneeId);
    List<Semestre> findByAnneeFiliereId(Long filiereId);
    // SemestreRepository.java
    @Query("SELECT DISTINCT s FROM Semestre s JOIN s.modules m WHERE m.filiere.id = :filiereId")
    List<Semestre> findByFiliereId(@Param("filiereId") Long filiereId);}