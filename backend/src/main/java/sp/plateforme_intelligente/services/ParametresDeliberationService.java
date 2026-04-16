package sp.plateforme_intelligente.services;

import sp.plateforme_intelligente.dto.ParametresDeliberationDTO;
import sp.plateforme_intelligente.models.ParametresDeliberation;
import sp.plateforme_intelligente.repositories.ParametresDeliberationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParametresDeliberationService {
    
    @Autowired
    private ParametresDeliberationRepository parametresRepository;
    
    public List<ParametresDeliberationDTO> getAllParametres() {
        return parametresRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    public ParametresDeliberationDTO getParametresById(Long id) {
        ParametresDeliberation params = parametresRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Paramètres non trouvés"));
        return convertToDTO(params);
    }
    
    public ParametresDeliberationDTO getParametresByFiliere(Long filiereId) {
        ParametresDeliberation params = parametresRepository.findByFiliereIdAndActifTrue(filiereId)
            .orElseThrow(() -> new RuntimeException("Paramètres non trouvés pour cette filière"));
        return convertToDTO(params);
    }
    
    public ParametresDeliberationDTO createParametres(ParametresDeliberation params) {
        // Désactiver les anciens paramètres de cette filière
        parametresRepository.findByFiliereId(params.getFiliere().getId())
            .ifPresent(anciens -> {
                anciens.setActif(false);
                parametresRepository.save(anciens);
            });
        
        params.setActif(true);
        ParametresDeliberation saved = parametresRepository.save(params);
        return convertToDTO(saved);
    }
    
    public ParametresDeliberationDTO updateParametres(Long id, ParametresDeliberation paramsDetails) {
        ParametresDeliberation params = parametresRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Paramètres non trouvés"));
        
        params.setNoteMinModule(paramsDetails.getNoteMinModule());
        params.setMoyenneMinSemestre(paramsDetails.getMoyenneMinSemestre());
        params.setNoteEliminatoire(paramsDetails.getNoteEliminatoire());
        params.setMoyenneMinAnnuelle(paramsDetails.getMoyenneMinAnnuelle());
        params.setMaxModulesParSemestre(paramsDetails.getMaxModulesParSemestre());
        
        ParametresDeliberation updated = parametresRepository.save(params);
        return convertToDTO(updated);
    }
    
    public void deleteParametres(Long id) {
        if (!parametresRepository.existsById(id)) {
            throw new RuntimeException("Paramètres non trouvés");
        }
        parametresRepository.deleteById(id);
    }
    
    private ParametresDeliberationDTO convertToDTO(ParametresDeliberation params) {
        ParametresDeliberationDTO dto = new ParametresDeliberationDTO();
        dto.setId(params.getId());
        dto.setNoteMinModule(params.getNoteMinModule());
        dto.setMoyenneMinSemestre(params.getMoyenneMinSemestre());
        dto.setNoteEliminatoire(params.getNoteEliminatoire());
        dto.setMoyenneMinAnnuelle(params.getMoyenneMinAnnuelle());
        dto.setMaxModulesParSemestre(params.getMaxModulesParSemestre());
        dto.setActif(params.isActif());
        
        if (params.getFiliere() != null) {
            dto.setFiliereId(params.getFiliere().getId());
            dto.setFiliereNom(params.getFiliere().getNom());
        }
        
        return dto;
    }
}