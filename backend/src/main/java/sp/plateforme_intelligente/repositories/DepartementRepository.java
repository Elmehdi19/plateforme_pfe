package sp.plateforme_intelligente.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sp.plateforme_intelligente.models.Departement;

public interface DepartementRepository extends JpaRepository<Departement, Long> {
    
    Optional<Departement> findByNom(String nom);
    
    Optional<Departement> findByCode(String code);
    
    List<Departement> findByEtablissementId(Long etablissementId);
    
    @Query("SELECT d FROM Departement d WHERE d.nom LIKE %:keyword% OR d.code LIKE %:keyword%")
    List<Departement> rechercher(@Param("keyword") String keyword);
}