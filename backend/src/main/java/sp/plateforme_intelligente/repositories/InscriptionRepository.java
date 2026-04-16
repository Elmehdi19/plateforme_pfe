package sp.plateforme_intelligente.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sp.plateforme_intelligente.models.Inscription;

public interface InscriptionRepository extends JpaRepository<Inscription, Long> {
    
    List<Inscription> findByEtudiantId(Long etudiantId);
    
    List<Inscription> findByAnneeUniversitaireId(Long anneeUnivId);
    
    List<Inscription> findBySemestreId(Long semestreId);
    
    List<Inscription> findByType(String type);
    
    @Query("SELECT i FROM Inscription i WHERE i.etudiant.id = :etudiantId AND i.anneeUniversitaire.id = :anneeUnivId")
    List<Inscription> findByEtudiantAndAnneeUniv(@Param("etudiantId") Long etudiantId, 
                                                 @Param("anneeUnivId") Long anneeUnivId);
    
    @Query("SELECT i FROM Inscription i WHERE i.etudiant.id = :etudiantId AND i.semestre.id = :semestreId")
    Optional<Inscription> findByEtudiantAndSemestre(@Param("etudiantId") Long etudiantId, 
                                                    @Param("semestreId") Long semestreId);
    
    boolean existsByEtudiantIdAndSemestreId(Long etudiantId, Long semestreId);
}