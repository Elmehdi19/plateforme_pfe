package sp.plateforme_intelligente.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sp.plateforme_intelligente.dto.AnneeDTO;
import sp.plateforme_intelligente.models.Annee;
import sp.plateforme_intelligente.repositories.AnneeRepository;

@Service
public class AnneeService {
    
    @Autowired
    private AnneeRepository anneeRepository;
    
    public List<AnneeDTO> getAllAnnees() {
        return anneeRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    public AnneeDTO getAnneeById(Long id) {
        Annee annee = anneeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Année non trouvée avec l'id: " + id));
        return convertToDTO(annee);
    }
    
    public AnneeDTO createAnnee(Annee annee) {
        Annee saved = anneeRepository.save(annee);
        return convertToDTO(saved);
    }
    
    public AnneeDTO updateAnnee(Long id, Annee anneeDetails) {
        Annee annee = anneeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Année non trouvée avec l'id: " + id));
        
        annee.setNom(anneeDetails.getNom());
        annee.setOrdre(anneeDetails.getOrdre());
        annee.setDiplômante(anneeDetails.isDiplômante());
        
        Annee updated = anneeRepository.save(annee);
        return convertToDTO(updated);
    }
    
    public void deleteAnnee(Long id) {
        if (!anneeRepository.existsById(id)) {
            throw new RuntimeException("Année non trouvée avec l'id: " + id);
        }
        anneeRepository.deleteById(id);
    }
    
    public List<AnneeDTO> getAnneesByFiliere(Long filiereId) {
        return anneeRepository.findByFiliereId(filiereId).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    private AnneeDTO convertToDTO(Annee annee) {
        AnneeDTO dto = new AnneeDTO();
        dto.setId(annee.getId());
        dto.setNom(annee.getNom());
        dto.setOrdre(annee.getOrdre());
        dto.setDiplômante(annee.isDiplômante());
        
        if (annee.getFiliere() != null) {
            dto.setFiliereId(annee.getFiliere().getId());
            dto.setFiliereNom(annee.getFiliere().getNom());
        }
        
        return dto;
    }
}