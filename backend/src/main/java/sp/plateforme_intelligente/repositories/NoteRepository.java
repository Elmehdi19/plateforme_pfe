package sp.plateforme_intelligente.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sp.plateforme_intelligente.models.Note;

public interface NoteRepository extends JpaRepository<Note, Long> {
    
    List<Note> findByElementId(Long elementId);
    
    List<Note> findByEtudiantId(Long etudiantId);
    
    @Query("SELECT n FROM Note n WHERE n.element.id = :elementId AND n.etudiant.id = :etudiantId")
    List<Note> findByElementIdAndEtudiantId(@Param("elementId") Long elementId, 
                                            @Param("etudiantId") Long etudiantId);
    
    @Query("SELECT n FROM Note n WHERE n.etudiant.id = :etudiantId AND n.session = :session")
    List<Note> findByEtudiantIdAndSession(@Param("etudiantId") Long etudiantId, 
                                          @Param("session") String session);
    
    @Query("SELECT AVG(n.valeur) FROM Note n WHERE n.element.module.id = :moduleId AND n.etudiant.id = :etudiantId")
    Double calculerMoyenneModule(@Param("etudiantId") Long etudiantId, 
                                 @Param("moduleId") Long moduleId);
    
    @Query("SELECT AVG(n.valeur) FROM Note n WHERE n.element.module.semestre.id = :semestreId AND n.etudiant.id = :etudiantId")
    Double calculerMoyenneSemestre(@Param("etudiantId") Long etudiantId, 
                                   @Param("semestreId") Long semestreId);
}