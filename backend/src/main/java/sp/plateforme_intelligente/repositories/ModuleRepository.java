package sp.plateforme_intelligente.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sp.plateforme_intelligente.models.Module;

public interface ModuleRepository extends JpaRepository<Module, Long> {
    
    Optional<Module> findByCode(String code);
    
    List<Module> findBySemestreId(Long semestreId);
    
    List<Module> findByProfesseurId(Long professeurId);
        
    boolean existsByCode(String code);
    
    @Query("SELECT m FROM Module m WHERE m.code LIKE %:keyword% OR m.intitule LIKE %:keyword%")
    List<Module> rechercher(@Param("keyword") String keyword);
    
    @Query("SELECT COUNT(m) FROM Module m WHERE m.semestre.id = :semestreId")
    long countBySemestreId(@Param("semestreId") Long semestreId);
}
