package sp.plateforme_intelligente.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sp.plateforme_intelligente.models.Filiere;

public interface FiliereRepository extends JpaRepository<Filiere, Long> {
    
    Optional<Filiere> findByNom(String nom);
    
    Optional<Filiere> findByCode(String code);
    
    List<Filiere> findByDepartementId(Long departementId);
    
    @Query("SELECT f FROM Filiere f WHERE f.nom LIKE %:keyword% OR f.code LIKE %:keyword%")
    List<Filiere> rechercher(@Param("keyword") String keyword);
}