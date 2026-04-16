package sp.plateforme_intelligente.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sp.plateforme_intelligente.dto.SemestreDTO;
import sp.plateforme_intelligente.models.Semestre;
import sp.plateforme_intelligente.repositories.SemestreRepository;

@Service
public class SemestreService {
    
    @Autowired
    private SemestreRepository semestreRepository;
    
    public List<SemestreDTO> getAllSemestres() {
        return semestreRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    public SemestreDTO getSemestreById(Long id) {
        Semestre semestre = semestreRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Semestre non trouvé avec l'id: " + id));
        return convertToDTO(semestre);
    }
    
    public SemestreDTO createSemestre(Semestre semestre) {
        Semestre saved = semestreRepository.save(semestre);
        return convertToDTO(saved);
    }
    
    public SemestreDTO updateSemestre(Long id, Semestre semestreDetails) {
        Semestre semestre = semestreRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Semestre non trouvé avec l'id: " + id));
        
        semestre.setNom(semestreDetails.getNom());
        semestre.setOrdre(semestreDetails.getOrdre());
        
        Semestre updated = semestreRepository.save(semestre);
        return convertToDTO(updated);
    }
    
    public void deleteSemestre(Long id) {
        if (!semestreRepository.existsById(id)) {
            throw new RuntimeException("Semestre non trouvé avec l'id: " + id);
        }
        semestreRepository.deleteById(id);
    }
    
    public List<SemestreDTO> getSemestresByAnnee(Long anneeId) {
        return semestreRepository.findByAnneeId(anneeId).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    // SemestreService.java
    public List<SemestreDTO> getSemestresByFiliere(Long filiereId) {
    return semestreRepository.findByFiliereId(filiereId).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
}
    
    private SemestreDTO convertToDTO(Semestre semestre) {
        SemestreDTO dto = new SemestreDTO();
        dto.setId(semestre.getId());
        dto.setNom(semestre.getNom());
        dto.setOrdre(semestre.getOrdre());
        
        if (semestre.getAnnee() != null) {
            dto.setAnneeId(semestre.getAnnee().getId());
            dto.setAnneeNom(semestre.getAnnee().getNom());
        }
        
        return dto;
    }
}