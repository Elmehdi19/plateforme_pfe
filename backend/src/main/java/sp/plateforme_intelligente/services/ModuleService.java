package sp.plateforme_intelligente.services;

import java.util.List;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sp.plateforme_intelligente.dto.ModuleDTO;
import sp.plateforme_intelligente.models.Module;
import sp.plateforme_intelligente.models.Element;
import sp.plateforme_intelligente.repositories.ModuleRepository;
import sp.plateforme_intelligente.repositories.ElementRepository;
@Service
public class ModuleService {
    
    @Autowired
    private ModuleRepository moduleRepository;
    @Autowired
    private ElementRepository elementRepository;
    
    public List<ModuleDTO> getAllModules() {
        return moduleRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    public ModuleDTO getModuleById(Long id) {
        Module module = moduleRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Module non trouvé avec l'id: " + id));
        return convertToDTO(module);
    }
    
    public ModuleDTO createModule(Module module) {
        if (moduleRepository.findByCode(module.getCode()).isPresent()) {
            throw new RuntimeException("Un module avec le code '" + module.getCode() + "' existe déjà");
        }
        Module saved = moduleRepository.save(module);
        return convertToDTO(saved);
    }
    
    public ModuleDTO updateModule(Long id, Module moduleDetails) {
        Module module = moduleRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Module non trouvé avec l'id: " + id));
        
        module.setCode(moduleDetails.getCode());
        module.setIntitule(moduleDetails.getIntitule());
        module.setCredits(moduleDetails.getCredits());
        module.setCoefficient(moduleDetails.getCoefficient());
        
        Module updated = moduleRepository.save(module);
        return convertToDTO(updated);
    }
    
   public void deleteModule(Long id) {
        Module module = moduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Module non trouvé"));

        // 1. Supprimer tous les éléments de ce module (et leurs notes, grâce à cascade)
        List<Element> elements = elementRepository.findByModuleId(id);
        elementRepository.deleteAll(elements);

        // 2. Supprimer le module
        moduleRepository.delete(module);
    }
    public List<ModuleDTO> getModulesBySemestre(Long semestreId) {
        return moduleRepository.findBySemestreId(semestreId).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    public List<ModuleDTO> getModulesByProfesseur(Long professeurId) {
        return moduleRepository.findByProfesseurId(professeurId).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    public List<ModuleDTO> rechercherModules(String keyword) {
        return moduleRepository.rechercher(keyword).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    private ModuleDTO convertToDTO(Module module) {
        ModuleDTO dto = new ModuleDTO();
        dto.setId(module.getId());
        dto.setCode(module.getCode());
        dto.setIntitule(module.getIntitule());
        dto.setCredits(module.getCredits());
        dto.setCoefficient(module.getCoefficient());
        
        if (module.getSemestre() != null) {
            dto.setSemestreId(module.getSemestre().getId());
            dto.setSemestreNom(module.getSemestre().getNom());
        }
        
        if (module.getFiliere() != null) {
            dto.setFiliereId(module.getFiliere().getId());
            dto.setFiliereNom(module.getFiliere().getNom());
        }
        
        if (module.getProfesseur() != null) {
            dto.setProfesseurId(module.getProfesseur().getId());
            dto.setProfesseurNom(module.getProfesseur().getUser() != null ? 
                module.getProfesseur().getUser().getNom() : null);
        }
        
        return dto;
    }
}