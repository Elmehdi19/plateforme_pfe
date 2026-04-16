package sp.plateforme_intelligente.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sp.plateforme_intelligente.models.Prediction;

public interface PredictionRepository extends JpaRepository<Prediction, Long> {
    
    List<Prediction> findByEtudiantId(Long etudiantId);
    
    @Query("SELECT p FROM Prediction p WHERE p.etudiant.id = :etudiantId ORDER BY p.datePrediction DESC")
    List<Prediction> findLastByEtudiantId(@Param("etudiantId") Long etudiantId);
    
    List<Prediction> findByNiveauRisque(String niveauRisque);
    
    @Query("SELECT COUNT(p) FROM Prediction p WHERE p.niveauRisque = :niveau")
    long countByNiveauRisque(@Param("niveau") String niveau);
    
    @Query(value = "SELECT * FROM predictions WHERE date_prediction >= DATE_SUB(CURDATE(), INTERVAL 7 DAY)", 
           nativeQuery = true)
    List<Prediction> findRecentPredictions();
}