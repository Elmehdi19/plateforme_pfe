package sp.plateforme_intelligente.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import sp.plateforme_intelligente.models.Element;

public interface ElementRepository extends JpaRepository<Element, Long> {
    
    List<Element> findByModuleId(Long moduleId);
    
    List<Element> findByProfesseurId(Long professeurId);
    
    List<Element> findByModuleSemestreId(Long semestreId);
}