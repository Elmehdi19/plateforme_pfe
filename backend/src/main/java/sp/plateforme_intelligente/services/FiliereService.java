package sp.plateforme_intelligente.services;

import sp.plateforme_intelligente.dto.FiliereDTO;
import sp.plateforme_intelligente.models.Filiere;
import sp.plateforme_intelligente.repositories.FiliereRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FiliereService {
    
    @Autowired
    private FiliereRepository filiereRepository;
    
    public List<FiliereDTO> getAllFilieres() {
        return filiereRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    public FiliereDTO getFiliereById(Long id) {
        Filiere filiere = filiereRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Filière non trouvée avec l'id: " + id));
        return convertToDTO(filiere);
    }
    
    public FiliereDTO createFiliere(Filiere filiere) {
        Filiere saved = filiereRepository.save(filiere);
        return convertToDTO(saved);
    }
    
    public FiliereDTO updateFiliere(Long id, Filiere filiereDetails) {
        Filiere filiere = filiereRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Filière non trouvée avec l'id: " + id));
        
        filiere.setNom(filiereDetails.getNom());
        filiere.setCode(filiereDetails.getCode());
        filiere.setDepartement(filiereDetails.getDepartement());
        
        Filiere updated = filiereRepository.save(filiere);
        return convertToDTO(updated);
    }
    
    public void deleteFiliere(Long id) {
        if (!filiereRepository.existsById(id)) {
            throw new RuntimeException("Filière non trouvée avec l'id: " + id);
        }
        filiereRepository.deleteById(id);
    }
    
    public List<FiliereDTO> getFilieresByDepartement(Long departementId) {
        return filiereRepository.findByDepartementId(departementId).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    private FiliereDTO convertToDTO(Filiere filiere) {
        FiliereDTO dto = new FiliereDTO();
        dto.setId(filiere.getId());
        dto.setNom(filiere.getNom());
        dto.setCode(filiere.getCode());
        
        if (filiere.getDepartement() != null) {
            dto.setDepartementId(filiere.getDepartement().getId());
            dto.setDepartementNom(filiere.getDepartement().getNom());
        }
        
        return dto;
    }
}