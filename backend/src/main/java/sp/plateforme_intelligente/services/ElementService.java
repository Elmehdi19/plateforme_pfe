package sp.plateforme_intelligente.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sp.plateforme_intelligente.dto.ElementDTO;
import sp.plateforme_intelligente.models.Element;
import sp.plateforme_intelligente.models.Module;
import sp.plateforme_intelligente.models.Note;
import sp.plateforme_intelligente.models.Professeur;
import sp.plateforme_intelligente.repositories.ElementRepository;
import sp.plateforme_intelligente.repositories.ModuleRepository;
import sp.plateforme_intelligente.repositories.NoteRepository;
import sp.plateforme_intelligente.repositories.ProfesseurRepository;

@Service
public class ElementService {
    
    @Autowired
    private ElementRepository elementRepository;
    
    @Autowired
    private ModuleRepository moduleRepository;
    
    @Autowired
    private ProfesseurRepository professeurRepository;
    
    @Autowired
    private NoteRepository noteRepository;
    
    public List<ElementDTO> getAllElements() {
        return elementRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    public ElementDTO getElementById(Long id) {
        Element element = elementRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Élément non trouvé avec l'id: " + id));
        return convertToDTO(element);
    }
    
    public ElementDTO createElement(Element element) {
        // Associer le module si fourni
        if (element.getModule() != null && element.getModule().getId() != null) {
            Module module = moduleRepository.findById(element.getModule().getId())
                    .orElseThrow(() -> new RuntimeException("Module non trouvé"));
            element.setModule(module);
        }
        // Associer le professeur si fourni
        if (element.getProfesseur() != null && element.getProfesseur().getId() != null) {
            Professeur professeur = professeurRepository.findById(element.getProfesseur().getId())
                    .orElseThrow(() -> new RuntimeException("Professeur non trouvé"));
            element.setProfesseur(professeur);
        }
        Element saved = elementRepository.save(element);
        return convertToDTO(saved);
    }
    
    public ElementDTO updateElement(Long id, Element elementDetails) {
        Element element = elementRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Élément non trouvé"));
        
        element.setNom(elementDetails.getNom());
        element.setCode(elementDetails.getCode());
        element.setCoefficient(elementDetails.getCoefficient());
        
        // Mise à jour du module
        if (elementDetails.getModule() != null && elementDetails.getModule().getId() != null) {
            Module module = moduleRepository.findById(elementDetails.getModule().getId())
                    .orElseThrow(() -> new RuntimeException("Module non trouvé"));
            element.setModule(module);
        } else {
            element.setModule(null);
        }
        
        // Mise à jour du professeur
        if (elementDetails.getProfesseur() != null && elementDetails.getProfesseur().getId() != null) {
            Professeur professeur = professeurRepository.findById(elementDetails.getProfesseur().getId())
                    .orElseThrow(() -> new RuntimeException("Professeur non trouvé"));
            element.setProfesseur(professeur);
        } else {
            element.setProfesseur(null);
        }
        
        Element updated = elementRepository.save(element);
        return convertToDTO(updated);
    }
    
    public void deleteElement(Long id) {
        Element element = elementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Élément non trouvé"));
        // Supprimer les notes associées
        List<Note> notes = noteRepository.findByElementId(id);
        noteRepository.deleteAll(notes);
        elementRepository.delete(element);
    }
    
    public List<ElementDTO> getElementsByModule(Long moduleId) {
        return elementRepository.findByModuleId(moduleId).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    public List<ElementDTO> getElementsByProfesseur(Long professeurId) {
        return elementRepository.findByProfesseurId(professeurId).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    private ElementDTO convertToDTO(Element element) {
        ElementDTO dto = new ElementDTO();
        dto.setId(element.getId());
        dto.setNom(element.getNom());
        dto.setCode(element.getCode());
        dto.setCoefficient(element.getCoefficient());
        
        if (element.getModule() != null) {
            dto.setModuleId(element.getModule().getId());
            dto.setModuleIntitule(element.getModule().getIntitule());
        }
        
        if (element.getProfesseur() != null) {
            dto.setProfesseurId(element.getProfesseur().getId());
            dto.setProfesseurNom(element.getProfesseur().getUser() != null ?
                    element.getProfesseur().getUser().getNom() : null);
        }
        
        return dto;
    }
}