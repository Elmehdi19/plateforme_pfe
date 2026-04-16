package sp.plateforme_intelligente.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import sp.plateforme_intelligente.dto.ProfesseurDTO;
import sp.plateforme_intelligente.models.Element;
import sp.plateforme_intelligente.models.Module;
import sp.plateforme_intelligente.models.Professeur;
import sp.plateforme_intelligente.models.Role;
import sp.plateforme_intelligente.models.User;
import sp.plateforme_intelligente.repositories.ElementRepository;
import sp.plateforme_intelligente.repositories.ModuleRepository;
import sp.plateforme_intelligente.repositories.ProfesseurRepository;
import sp.plateforme_intelligente.repositories.UserRepository;

@Service
public class ProfesseurService {

    @Autowired
    private ProfesseurRepository professeurRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModuleRepository moduleRepository;      // ← ajout

    @Autowired
    private ElementRepository elementRepository;    // ← ajout

    public List<ProfesseurDTO> getAllProfesseurs() {
        return professeurRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ProfesseurDTO getProfesseurById(Long id) {
        Professeur professeur = professeurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Professeur non trouvé avec l'id: " + id));
        return convertToDTO(professeur);
    }

    public ProfesseurDTO createProfesseur(Professeur professeur) {
        if (professeur.getUser() == null || professeur.getUser().getEmail() == null) {
            throw new RuntimeException("Email requis");
        }
        if (userRepository.findByEmail(professeur.getUser().getEmail()).isPresent()) {
            throw new RuntimeException("Email déjà utilisé");
        }

        User user = new User();
        user.setNom(professeur.getUser().getNom() != null ? professeur.getUser().getNom() : "Professeur");
        user.setEmail(professeur.getUser().getEmail());
        user.setMotDePasse(passwordEncoder.encode("default123"));
        user.setRole(Role.PROFESSEUR);
        user.setDateCreation(LocalDate.now());
        User savedUser = userRepository.save(user);

        professeur.setUser(savedUser);
        Professeur saved = professeurRepository.save(professeur);
        return convertToDTO(saved);
    }

    public ProfesseurDTO updateProfesseur(Long id, Professeur professeurDetails) {
        Professeur professeur = professeurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Professeur non trouvé avec l'id: " + id));

        professeur.setBureau(professeurDetails.getBureau());
        professeur.setSpecialite(professeurDetails.getSpecialite());
        professeur.setDiscipline(professeurDetails.getDiscipline());
        professeur.setDepartement(professeurDetails.getDepartement());
        professeur.setResponsableFiliere(professeurDetails.isResponsableFiliere());

        if (professeurDetails.getUser() != null && professeurDetails.getUser().getNom() != null) {
            User user = professeur.getUser();
            user.setNom(professeurDetails.getUser().getNom());
            userRepository.save(user);
        }

        Professeur updated = professeurRepository.save(professeur);
        return convertToDTO(updated);
    }

    // 🔥 Méthode corrigée pour supprimer un professeur en dissociant les références
    public void deleteProfesseur(Long id) {
        Professeur professeur = professeurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Professeur non trouvé"));

        // 1. Dissocier les modules qui référencent ce professeur
        List<Module> modules = moduleRepository.findByProfesseurId(id);
        for (Module module : modules) {
            module.setProfesseur(null);
            moduleRepository.save(module);
        }

        // 2. Dissocier les éléments qui référencent ce professeur
        List<Element> elements = elementRepository.findByProfesseurId(id);
        for (Element element : elements) {
            element.setProfesseur(null);
            elementRepository.save(element);
        }

        // 3. Supprimer l'utilisateur associé (optionnel, mais recommandé)
        if (professeur.getUser() != null) {
            userRepository.delete(professeur.getUser());
        }

        // 4. Supprimer le professeur
        professeurRepository.delete(professeur);
    }

    public List<ProfesseurDTO> rechercherProfesseurs(String keyword) {
        return professeurRepository.rechercher(keyword).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ProfesseurDTO> getResponsablesFiliere() {
        return professeurRepository.findByResponsableFiliereTrue().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ProfesseurDTO convertToDTO(Professeur professeur) {
        ProfesseurDTO dto = new ProfesseurDTO();
        dto.setId(professeur.getId());
        dto.setBureau(professeur.getBureau());
        dto.setSpecialite(professeur.getSpecialite());
        dto.setDiscipline(professeur.getDiscipline());
        dto.setDepartement(professeur.getDepartement());
        dto.setResponsableFiliere(professeur.isResponsableFiliere());

        if (professeur.getUser() != null) {
            dto.setNom(professeur.getUser().getNom());
            dto.setEmail(professeur.getUser().getEmail());
        }

        return dto;
    }
}