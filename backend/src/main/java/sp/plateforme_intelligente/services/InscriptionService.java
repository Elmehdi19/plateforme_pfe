package sp.plateforme_intelligente.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sp.plateforme_intelligente.dto.InscriptionDTO;
import sp.plateforme_intelligente.models.Annee;
import sp.plateforme_intelligente.models.AnneeUniversitaire;
import sp.plateforme_intelligente.models.Etudiant;
import sp.plateforme_intelligente.models.Filiere;
import sp.plateforme_intelligente.models.Inscription;
import sp.plateforme_intelligente.models.ParametresDeliberation;
import sp.plateforme_intelligente.models.Semestre;
import sp.plateforme_intelligente.repositories.AnneeUniversitaireRepository;
import sp.plateforme_intelligente.repositories.EtudiantRepository;
import sp.plateforme_intelligente.repositories.InscriptionRepository;
import sp.plateforme_intelligente.repositories.ParametresDeliberationRepository;
import sp.plateforme_intelligente.repositories.SemestreRepository;

@Service
public class InscriptionService {
    
    @Autowired
    private InscriptionRepository inscriptionRepository;
    
    @Autowired
    private EtudiantRepository etudiantRepository;
    
    @Autowired
    private AnneeUniversitaireRepository anneeUniversitaireRepository;
    
    @Autowired
    private SemestreRepository semestreRepository;
    
    
    @Autowired
    private ParametresDeliberationRepository parametresRepository;
    
    public List<InscriptionDTO> getAllInscriptions() {
        return inscriptionRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    public InscriptionDTO getInscriptionById(Long id) {
        Inscription inscription = inscriptionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Inscription non trouvée"));
        return convertToDTO(inscription);
    }
    
    @Transactional
    public InscriptionDTO inscriptionEnLigne(Inscription inscription) {
        inscription.setType("EN_LIGNE");
        inscription.setDateInscription(LocalDate.now());
        
        Inscription saved = inscriptionRepository.save(inscription);
        return convertToDTO(saved);
    }
    
    @Transactional
    public InscriptionDTO inscriptionAdministrative(Long etudiantId, Long anneeUnivId, Inscription details) {
        Etudiant etudiant = etudiantRepository.findById(etudiantId)
            .orElseThrow(() -> new RuntimeException("Étudiant non trouvé"));
        
        AnneeUniversitaire anneeUniv = anneeUniversitaireRepository.findById(anneeUnivId)
            .orElseThrow(() -> new RuntimeException("Année universitaire non trouvée"));
        
        Inscription inscription = new Inscription();
        inscription.setEtudiant(etudiant);
        inscription.setAnneeUniversitaire(anneeUniv);
        inscription.setType("ADMINISTRATIVE");
        inscription.setDateInscription(LocalDate.now());
        inscription.setAdresse(details.getAdresse());
        inscription.setVille(details.getVille());
        inscription.setTelephone(details.getTelephone());
        inscription.setBourse(details.getBourse());
        
        Inscription saved = inscriptionRepository.save(inscription);
        
        // Inscription pédagogique automatique au premier semestre
        inscrirePedagogiqueAutomatique(etudiant, anneeUniv);
        
        return convertToDTO(saved);
    }
    
    @Transactional
    public void inscrirePedagogiqueAutomatique(Etudiant etudiant, AnneeUniversitaire anneeUniv) {
        Filiere filiere = etudiant.getFiliere();
        if (filiere == null) return;
        
        List<Annee> annees = filiere.getAnnees();
        if (annees == null || annees.isEmpty()) return;
        
        Annee premiereAnnee = annees.stream()
            .filter(a -> a.getOrdre() == 1)
            .findFirst().orElse(null);
        
        if (premiereAnnee == null) return;
        
        for (Semestre semestre : premiereAnnee.getSemestres()) {
            // Vérifier si déjà inscrit
            if (inscriptionRepository.existsByEtudiantIdAndSemestreId(etudiant.getId(), semestre.getId())) {
                continue;
            }
            
            Inscription inscription = new Inscription();
            inscription.setEtudiant(etudiant);
            inscription.setAnneeUniversitaire(anneeUniv);
            inscription.setSemestre(semestre);
            inscription.setType("PEDAGOGIQUE");
            inscription.setDateInscription(LocalDate.now());
            inscription.setInscritAutomatiquement(true);
            
            inscriptionRepository.save(inscription);
        }
    }
    
    @Transactional
    public void inscrireAuSemestre(Long etudiantId, Long semestreId, Long anneeUnivId) {
        Etudiant etudiant = etudiantRepository.findById(etudiantId)
            .orElseThrow(() -> new RuntimeException("Étudiant non trouvé"));
        
        Semestre semestre = semestreRepository.findById(semestreId)
            .orElseThrow(() -> new RuntimeException("Semestre non trouvé"));
        
        AnneeUniversitaire anneeUniv = anneeUniversitaireRepository.findById(anneeUnivId)
            .orElseThrow(() -> new RuntimeException("Année universitaire non trouvée"));
        
        // Vérifier si déjà inscrit
        if (inscriptionRepository.existsByEtudiantIdAndSemestreId(etudiantId, semestreId)) {
            throw new RuntimeException("L'étudiant est déjà inscrit à ce semestre");
        }
        
        // Vérifier le nombre maximum de modules
        ParametresDeliberation params = parametresRepository
            .findByFiliereIdAndActifTrue(etudiant.getFiliere().getId())
            .orElse(null);
        
        Integer maxModulesValue = params != null ? params.getMaxModulesParSemestre() : null;
        int maxModules = maxModulesValue != null ? maxModulesValue : 7;
        
        // Compter les modules du semestre
        int nbModules = semestre.getModules().size();
        if (nbModules > maxModules) {
            throw new RuntimeException("Ce semestre contient " + nbModules + 
                                      " modules, maximum autorisé: " + maxModules);
        }
        
        Inscription inscription = new Inscription();
        inscription.setEtudiant(etudiant);
        inscription.setAnneeUniversitaire(anneeUniv);
        inscription.setSemestre(semestre);
        inscription.setType("PEDAGOGIQUE");
        inscription.setDateInscription(LocalDate.now());
        
        inscriptionRepository.save(inscription);
    }
    
    @Transactional
    public void desinscrireDuSemestre(Long etudiantId, Long semestreId) {
        Inscription inscription = inscriptionRepository.findByEtudiantAndSemestre(etudiantId, semestreId)
            .orElseThrow(() -> new RuntimeException("Inscription non trouvée"));
        
        inscriptionRepository.delete(inscription);
    }
    
    public List<InscriptionDTO> getInscriptionsByEtudiant(Long etudiantId) {
        return inscriptionRepository.findByEtudiantId(etudiantId).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    public List<InscriptionDTO> getInscriptionsByAnneeUniv(Long anneeUnivId) {
        return inscriptionRepository.findByAnneeUniversitaireId(anneeUnivId).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    public List<InscriptionDTO> getInscriptionsBySemestre(Long semestreId) {
        return inscriptionRepository.findBySemestreId(semestreId).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    public List<InscriptionDTO> getInscriptionsByType(String type) {
        return inscriptionRepository.findByType(type).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    private InscriptionDTO convertToDTO(Inscription inscription) {
        InscriptionDTO dto = new InscriptionDTO();
        dto.setId(inscription.getId());
        dto.setDateInscription(inscription.getDateInscription());
        dto.setType(inscription.getType());
        dto.setInscritAutomatiquement(inscription.isInscritAutomatiquement());
        
        if (inscription.getEtudiant() != null) {
            dto.setEtudiantId(inscription.getEtudiant().getId());
            dto.setEtudiantMatricule(inscription.getEtudiant().getMatricule());
            if (inscription.getEtudiant().getUser() != null) {
                dto.setEtudiantNom(inscription.getEtudiant().getUser().getNom());
            }
        }
        
        if (inscription.getAnneeUniversitaire() != null) {
            dto.setAnneeUniversitaireId(inscription.getAnneeUniversitaire().getId());
            dto.setAnneeUniversitaireCode(inscription.getAnneeUniversitaire().getCode());
        }
        
        if (inscription.getSemestre() != null) {
            dto.setSemestreId(inscription.getSemestre().getId());
            dto.setSemestreNom(inscription.getSemestre().getNom());
        }
        
        // Champs inscription administrative
        dto.setAdresse(inscription.getAdresse());
        dto.setVille(inscription.getVille());
        dto.setTelephone(inscription.getTelephone());
        dto.setBourse(inscription.getBourse());
        
        // Champs inscription en ligne
        dto.setMassarEtudiant(inscription.getMassarEtudiant());
        dto.setNomArabe(inscription.getNomArabe());
        dto.setPrenomArabe(inscription.getPrenomArabe());
        dto.setLieuNaissanceArabe(inscription.getLieuNaissanceArabe());
        
        return dto;
    }
}