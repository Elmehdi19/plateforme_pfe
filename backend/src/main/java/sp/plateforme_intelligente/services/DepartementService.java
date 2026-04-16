package sp.plateforme_intelligente.services;

import sp.plateforme_intelligente.dto.DepartementDTO;
import sp.plateforme_intelligente.models.Departement;
import sp.plateforme_intelligente.models.Etablissement;
import sp.plateforme_intelligente.repositories.DepartementRepository;
import sp.plateforme_intelligente.repositories.EtablissementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartementService {

    @Autowired
    private DepartementRepository departementRepository;

    @Autowired
    private EtablissementRepository etablissementRepository;

    public List<DepartementDTO> getAllDepartements() {
        return departementRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public DepartementDTO getDepartementById(Long id) {
        Departement departement = departementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Département non trouvé"));
        return convertToDTO(departement);
    }

    public DepartementDTO createDepartement(Departement departement) {
        if (departement.getEtablissement() != null && departement.getEtablissement().getId() != null) {
            Etablissement etablissement = etablissementRepository.findById(departement.getEtablissement().getId())
                    .orElseThrow(() -> new RuntimeException("Établissement non trouvé"));
            departement.setEtablissement(etablissement);
        }
        Departement saved = departementRepository.save(departement);
        return convertToDTO(saved);
    }

    public DepartementDTO updateDepartement(Long id, Departement departementDetails) {
        Departement departement = departementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Département non trouvé"));
        departement.setNom(departementDetails.getNom());
        departement.setCode(departementDetails.getCode());
        if (departementDetails.getEtablissement() != null && departementDetails.getEtablissement().getId() != null) {
            Etablissement etablissement = etablissementRepository.findById(departementDetails.getEtablissement().getId())
                    .orElseThrow(() -> new RuntimeException("Établissement non trouvé"));
            departement.setEtablissement(etablissement);
        } else {
            departement.setEtablissement(null);
        }
        Departement updated = departementRepository.save(departement);
        return convertToDTO(updated);
    }

    public void deleteDepartement(Long id) {
        Departement departement = departementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Département non trouvé"));
        // Attention : si des filières sont liées, il faudrait les dissocier ou supprimer.
        departementRepository.delete(departement);
    }

    public List<DepartementDTO> getDepartementsByEtablissement(Long etablissementId) {
        return departementRepository.findByEtablissementId(etablissementId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private DepartementDTO convertToDTO(Departement departement) {
        DepartementDTO dto = new DepartementDTO();
        dto.setId(departement.getId());
        dto.setNom(departement.getNom());
        dto.setCode(departement.getCode());
        if (departement.getEtablissement() != null) {
            dto.setEtablissementId(departement.getEtablissement().getId());
            dto.setEtablissementNom(departement.getEtablissement().getNom());
        }
        return dto;
    }
}