package sp.plateforme_intelligente.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sp.plateforme_intelligente.dto.AnneeUniversitaireDTO;
import sp.plateforme_intelligente.models.AnneeUniversitaire;
import sp.plateforme_intelligente.repositories.AnneeUniversitaireRepository;

@Service
public class AnneeUniversitaireService {
    
    @Autowired
    private AnneeUniversitaireRepository anneeUniversitaireRepository;
    
    public List<AnneeUniversitaireDTO> getAllAnneesUniversitaires() {
        return anneeUniversitaireRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    public AnneeUniversitaireDTO getAnneeUniversitaireById(Long id) {
        AnneeUniversitaire anneeUniv = anneeUniversitaireRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Année universitaire non trouvée"));
        return convertToDTO(anneeUniv);
    }
    
    public AnneeUniversitaireDTO getAnneeUniversitaireByCode(String code) {
        AnneeUniversitaire anneeUniv = anneeUniversitaireRepository.findByCode(code)
            .orElseThrow(() -> new RuntimeException("Année universitaire non trouvée"));
        return convertToDTO(anneeUniv);
    }
    
    public AnneeUniversitaireDTO getAnneeUniversitaireActive() {
        AnneeUniversitaire anneeUniv = anneeUniversitaireRepository.findByActiveTrue()
            .orElseThrow(() -> new RuntimeException("Aucune année universitaire active"));
        return convertToDTO(anneeUniv);
    }
    
    public AnneeUniversitaireDTO createAnneeUniversitaire(AnneeUniversitaire anneeUniv) {
        // Désactiver l'ancienne année active si celle-ci est active
        if (anneeUniv.isActive()) {
            anneeUniversitaireRepository.findByActiveTrue()
                .ifPresent(ancienne -> {
                    ancienne.setActive(false);
                    anneeUniversitaireRepository.save(ancienne);
                });
        }
        
        AnneeUniversitaire saved = anneeUniversitaireRepository.save(anneeUniv);
        return convertToDTO(saved);
    }
    
    public AnneeUniversitaireDTO updateAnneeUniversitaire(Long id, AnneeUniversitaire anneeDetails) {
        AnneeUniversitaire anneeUniv = anneeUniversitaireRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Année universitaire non trouvée"));
        
        anneeUniv.setCode(anneeDetails.getCode());
        anneeUniv.setDateDebut(anneeDetails.getDateDebut());
        anneeUniv.setDateFin(anneeDetails.getDateFin());
        
        // Gestion de l'activation
        if (anneeDetails.isActive() && !anneeUniv.isActive()) {
            anneeUniversitaireRepository.findByActiveTrue()
                .ifPresent(ancienne -> {
                    ancienne.setActive(false);
                    anneeUniversitaireRepository.save(ancienne);
                });
            anneeUniv.setActive(true);
        } else if (!anneeDetails.isActive()) {
            anneeUniv.setActive(false);
        }
        
        AnneeUniversitaire updated = anneeUniversitaireRepository.save(anneeUniv);
        return convertToDTO(updated);
    }
    
    public void deleteAnneeUniversitaire(Long id) {
        if (!anneeUniversitaireRepository.existsById(id)) {
            throw new RuntimeException("Année universitaire non trouvée");
        }
        anneeUniversitaireRepository.deleteById(id);
    }
    
    private AnneeUniversitaireDTO convertToDTO(AnneeUniversitaire anneeUniv) {
        AnneeUniversitaireDTO dto = new AnneeUniversitaireDTO();
        dto.setId(anneeUniv.getId());
        dto.setCode(anneeUniv.getCode());
        dto.setDateDebut(anneeUniv.getDateDebut());
        dto.setDateFin(anneeUniv.getDateFin());
        dto.setActive(anneeUniv.isActive());
        return dto;
    }
}