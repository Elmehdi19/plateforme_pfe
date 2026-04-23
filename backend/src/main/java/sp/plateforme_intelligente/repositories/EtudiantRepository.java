package sp.plateforme_intelligente.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sp.plateforme_intelligente.models.Etudiant;
import sp.plateforme_intelligente.models.Filiere;
import sp.plateforme_intelligente.models.User;

public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {
    
    Optional<Etudiant> findByMatricule(String matricule);

    List<Etudiant> findByFiliere(Filiere filiere);
    @Query("SELECT e.nbAbsences FROM Etudiant e WHERE e.id = :etudiantId")
    Integer getNbAbsences(@Param("etudiantId") Long etudiantId);
    
    List<Etudiant> findByFiliereId(Long filiereId);
    Optional<Etudiant> findByUser(User user);
    @Query("SELECT e FROM Etudiant e WHERE e.filiere.nom = :filiereNom")
    List<Etudiant> findByFiliereNom(@Param("filiereNom") String filiereNom);
    
    @Query("SELECT e FROM Etudiant e WHERE e.user.nom LIKE CONCAT('%', :keyword, '%') OR e.matricule LIKE CONCAT('%', :keyword, '%') OR e.cne LIKE CONCAT('%', :keyword, '%')")
    List<Etudiant> rechercher(@Param("keyword") String keyword);
}