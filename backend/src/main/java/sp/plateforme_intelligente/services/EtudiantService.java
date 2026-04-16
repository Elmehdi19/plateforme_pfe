package sp.plateforme_intelligente.services;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import sp.plateforme_intelligente.dto.EtudiantDTO;
import sp.plateforme_intelligente.models.Etudiant;
import sp.plateforme_intelligente.models.Filiere;
import sp.plateforme_intelligente.models.Inscription;
import sp.plateforme_intelligente.models.Note;
import sp.plateforme_intelligente.models.Role;
import sp.plateforme_intelligente.models.User;
import sp.plateforme_intelligente.repositories.EtudiantRepository;
import sp.plateforme_intelligente.repositories.InscriptionRepository;
import sp.plateforme_intelligente.repositories.NoteRepository;
import sp.plateforme_intelligente.repositories.UserRepository;

@Service
public class EtudiantService {
    
    @Autowired
    private EtudiantRepository etudiantRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private InscriptionRepository inscriptionRepository;
    
    @Autowired
    private NoteRepository noteRepository;
    
    // ==================== CRUD DE BASE ====================
    
    public List<EtudiantDTO> getAllEtudiants() {
        return etudiantRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    public EtudiantDTO getEtudiantById(Long id) {
        Etudiant etudiant = etudiantRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Étudiant non trouvé avec l'id: " + id));
        return convertToDTO(etudiant);
    }
    
    public EtudiantDTO createEtudiant(Etudiant etudiant) {
        if (etudiantRepository.findByMatricule(etudiant.getMatricule()).isPresent()) {
            throw new RuntimeException("Matricule '" + etudiant.getMatricule() + "' déjà existant");
        }
        
        User user = new User();
        user.setNom("Étudiant " + etudiant.getMatricule());
        user.setEmail(etudiant.getMatricule() + "@etudiant.com");
        user.setMotDePasse(passwordEncoder.encode("default123"));
        user.setRole(Role.ETUDIANT);
        user.setDateCreation(LocalDate.now());
        User savedUser = userRepository.save(user);
        
        etudiant.setUser(savedUser);
        Etudiant saved = etudiantRepository.save(etudiant);
        return convertToDTO(saved);
    }
    
    public EtudiantDTO updateEtudiant(Long id, Etudiant etudiantDetails) {
        Etudiant etudiant = etudiantRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Étudiant non trouvé avec l'id: " + id));
        
        etudiant.setMatricule(etudiantDetails.getMatricule());
        etudiant.setCne(etudiantDetails.getCne());
        etudiant.setCin(etudiantDetails.getCin());
        etudiant.setNationalite(etudiantDetails.getNationalite());
        etudiant.setSexe(etudiantDetails.getSexe());
        etudiant.setDateNaissance(etudiantDetails.getDateNaissance());
        etudiant.setLieuNaissance(etudiantDetails.getLieuNaissance());
        etudiant.setAdresse(etudiantDetails.getAdresse());
        etudiant.setVille(etudiantDetails.getVille());
        etudiant.setTelephone(etudiantDetails.getTelephone());
        etudiant.setBacAnnee(etudiantDetails.getBacAnnee());
        etudiant.setBacSerie(etudiantDetails.getBacSerie());
        etudiant.setBacMention(etudiantDetails.getBacMention());
        etudiant.setBacLycée(etudiantDetails.getBacLycée());
        etudiant.setBacLieuObtention(etudiantDetails.getBacLieuObtention());
        etudiant.setBacAcademie(etudiantDetails.getBacAcademie());
        
        if (etudiantDetails.getFiliere() != null) {
            etudiant.setFiliere(etudiantDetails.getFiliere());
        }
        
        Etudiant updated = etudiantRepository.save(etudiant);
        return convertToDTO(updated);
    }
    
    public void deleteEtudiant(Long id) {
        Etudiant etudiant = etudiantRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Étudiant non trouvé"));

        List<Note> notes = noteRepository.findByEtudiantId(id);
        noteRepository.deleteAll(notes);

        List<Inscription> inscriptions = inscriptionRepository.findByEtudiantId(id);
        inscriptionRepository.deleteAll(inscriptions);

        if (etudiant.getUser() != null) {
            userRepository.delete(etudiant.getUser());
        }

        etudiantRepository.delete(etudiant);
    }
    
    // ==================== RECHERCHE ====================
    
    public List<EtudiantDTO> rechercherEtudiants(String keyword) {
        return etudiantRepository.rechercher(keyword).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    public List<EtudiantDTO> getEtudiantsByFiliereId(Long filiereId) {
        return etudiantRepository.findByFiliereId(filiereId).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    public List<EtudiantDTO> getEtudiantsByFiliereNom(String filiereNom) {
        return etudiantRepository.findByFiliereNom(filiereNom).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    public List<EtudiantDTO> getEtudiantsByFiliere(Filiere filiere) {
        return etudiantRepository.findByFiliere(filiere).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    // ==================== INFORMATIONS COMPLÉMENTAIRES ====================
    
    public List<Object> getNotesEtudiant(Long etudiantId) {
        return noteRepository.findByEtudiantId(etudiantId).stream()
            .map(note -> Map.of(
                "id", note.getId(),
                "element", note.getElement() != null ? note.getElement().getNom() : null,
                "module", note.getElement() != null && note.getElement().getModule() != null ? 
                           note.getElement().getModule().getIntitule() : null,
                "valeur", note.getValeur(),
                "type", note.getTypeEvaluation(),
                "session", note.getSession(),
                "date", note.getDateEvaluation()
            ))
            .collect(Collectors.toList());
    }
    
    public List<Object> getInscriptionsEtudiant(Long etudiantId) {
        return inscriptionRepository.findByEtudiantId(etudiantId).stream()
            .map(inscription -> Map.of(
                "id", inscription.getId(),
                "anneeUniversitaire", inscription.getAnneeUniversitaire() != null ? 
                                        inscription.getAnneeUniversitaire().getCode() : null,
                "type", inscription.getType(),
                "date", inscription.getDateInscription(),
                "semestre", inscription.getSemestre() != null ? 
                             inscription.getSemestre().getNom() : null,
                "inscritAutomatiquement", inscription.isInscritAutomatiquement()
            ))
            .collect(Collectors.toList());
    }
    
    public Double calculerMoyenneGenerale(Long etudiantId) {
        return 0.0;
    }
    
    // ==================== CONVERSION ====================
    
    private EtudiantDTO convertToDTO(Etudiant etudiant) {
        EtudiantDTO dto = new EtudiantDTO();
        
        dto.setId(etudiant.getId());
        dto.setMatricule(etudiant.getMatricule());
        dto.setCne(etudiant.getCne());
        dto.setCin(etudiant.getCin());
        dto.setNationalite(etudiant.getNationalite());
        dto.setSexe(etudiant.getSexe());
        dto.setDateNaissance(etudiant.getDateNaissance());
        dto.setLieuNaissance(etudiant.getLieuNaissance());
        dto.setAdresse(etudiant.getAdresse());
        dto.setVille(etudiant.getVille());
        dto.setTelephone(etudiant.getTelephone());
        
        dto.setBacAnnee(etudiant.getBacAnnee());
        dto.setBacSerie(etudiant.getBacSerie());
        dto.setBacMention(etudiant.getBacMention());
        dto.setBacLycée(etudiant.getBacLycée());
        dto.setBacLieuObtention(etudiant.getBacLieuObtention());
        dto.setBacAcademie(etudiant.getBacAcademie());
        
        if (etudiant.getUser() != null) {
            dto.setNom(etudiant.getUser().getNom());
            dto.setEmail(etudiant.getUser().getEmail());
        }
        
        if (etudiant.getFiliere() != null) {
            dto.setFiliere(etudiant.getFiliere().getNom());
            dto.setFiliereId(etudiant.getFiliere().getId());
        }
        
        // Récupérer l'inscription la plus récente pour le semestre
        List<Inscription> inscriptions = inscriptionRepository.findByEtudiantId(etudiant.getId());
        Inscription derniere = inscriptions.stream()
            .max(Comparator.comparing(Inscription::getDateInscription))
            .orElse(null);
        if (derniere != null && derniere.getSemestre() != null) {
            dto.setSemestre(derniere.getSemestre().getNom());
        } else {
            dto.setSemestre("");
        }
        
        return dto;
    }
}