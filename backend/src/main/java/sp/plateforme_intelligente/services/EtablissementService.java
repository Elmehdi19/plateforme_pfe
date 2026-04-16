package sp.plateforme_intelligente.services;

import sp.plateforme_intelligente.dto.EtablissementDTO;
import sp.plateforme_intelligente.models.Etablissement;
import sp.plateforme_intelligente.repositories.EtablissementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EtablissementService {

    @Autowired
    private EtablissementRepository etablissementRepository;

    public List<EtablissementDTO> getAllEtablissements() {
        return etablissementRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public EtablissementDTO getEtablissementById(Long id) {
        Etablissement etablissement = etablissementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Établissement non trouvé"));
        return convertToDTO(etablissement);
    }

    public EtablissementDTO createEtablissement(Etablissement etablissement) {
        Etablissement saved = etablissementRepository.save(etablissement);
        return convertToDTO(saved);
    }

    public EtablissementDTO updateEtablissement(Long id, Etablissement etablissementDetails) {
        Etablissement etablissement = etablissementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Établissement non trouvé"));
        etablissement.setNom(etablissementDetails.getNom());
        etablissement.setCode(etablissementDetails.getCode());
        etablissement.setAdresse(etablissementDetails.getAdresse());
        Etablissement updated = etablissementRepository.save(etablissement);
        return convertToDTO(updated);
    }

    public void deleteEtablissement(Long id) {
        if (!etablissementRepository.existsById(id)) {
            throw new RuntimeException("Établissement non trouvé");
        }
        etablissementRepository.deleteById(id);
    }

    private EtablissementDTO convertToDTO(Etablissement etablissement) {
        EtablissementDTO dto = new EtablissementDTO();
        dto.setId(etablissement.getId());
        dto.setNom(etablissement.getNom());
        dto.setCode(etablissement.getCode());
        dto.setAdresse(etablissement.getAdresse());
        return dto;
    }
}