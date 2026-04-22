package sp.plateforme_intelligente.services;

import sp.plateforme_intelligente.dto.AbsenceDTO;
import sp.plateforme_intelligente.models.Absence;
import sp.plateforme_intelligente.repositories.AbsenceRepository;
import sp.plateforme_intelligente.repositories.EtudiantRepository;
import sp.plateforme_intelligente.repositories.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AbsenceService {

    @Autowired
    private AbsenceRepository absenceRepository;

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    public List<AbsenceDTO> getAllAbsences() {
        return absenceRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<AbsenceDTO> getAbsencesByEtudiant(Long etudiantId) {
        return absenceRepository.findByEtudiantId(etudiantId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public long getAbsenceCountByEtudiant(Long etudiantId) {
        return absenceRepository.countByEtudiantIdAndJustifieeFalse(etudiantId);
    }

    public AbsenceDTO createAbsence(Absence absence) {
        Absence saved = absenceRepository.save(absence);
        return convertToDTO(saved);
    }

    public void deleteAbsence(Long id) {
        absenceRepository.deleteById(id);
    }

    private AbsenceDTO convertToDTO(Absence absence) {
        AbsenceDTO dto = new AbsenceDTO();
        dto.setId(absence.getId());
        dto.setDateAbsence(absence.getDateAbsence());
        dto.setJustifiee(absence.getJustifiee());
        dto.setRaison(absence.getRaison());
        if (absence.getEtudiant() != null) {
            dto.setEtudiantId(absence.getEtudiant().getId());
            dto.setEtudiantNom(absence.getEtudiant().getUser().getNom());
        }
        if (absence.getModule() != null) {
            dto.setModuleId(absence.getModule().getId());
            dto.setModuleIntitule(absence.getModule().getIntitule());
        }
        return dto;
    }
}