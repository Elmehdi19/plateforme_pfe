package sp.plateforme_intelligente.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sp.plateforme_intelligente.models.Etablissement;

public interface EtablissementRepository extends JpaRepository<Etablissement, Long> {
    
    Optional<Etablissement> findByNom(String nom);
    
    Optional<Etablissement> findByCode(String code);
    
    @Query("SELECT e FROM Etablissement e WHERE e.nom LIKE %:keyword% OR e.code LIKE %:keyword%")
    List<Etablissement> rechercher(@Param("keyword") String keyword);
}