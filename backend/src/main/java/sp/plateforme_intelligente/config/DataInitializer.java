package sp.plateforme_intelligente.config;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import sp.plateforme_intelligente.models.Etudiant;
import sp.plateforme_intelligente.models.Professeur;
import sp.plateforme_intelligente.models.Role;
import sp.plateforme_intelligente.models.User;
import sp.plateforme_intelligente.repositories.EtudiantRepository;
import sp.plateforme_intelligente.repositories.ProfesseurRepository;
import sp.plateforme_intelligente.repositories.UserRepository;

@Component
public class DataInitializer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private ProfesseurRepository professeurRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void initUsers() {
        // --- Admin ---
        if (!userRepository.existsByEmail("admin@test.com")) {
            User admin = new User();
            admin.setEmail("admin@test.com");
            admin.setNom("Admin");
            admin.setMotDePasse(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ADMIN);
            admin.setDateCreation(LocalDate.now());
            userRepository.save(admin);
        }

        // --- Professeur ---
        if (!userRepository.existsByEmail("prof.dupont@test.com")) {
            User prof = new User();
            prof.setEmail("prof.dupont@test.com");
            prof.setNom("Professeur Dupont");
            prof.setMotDePasse(passwordEncoder.encode("default123"));
            prof.setRole(Role.PROFESSEUR);
            prof.setDateCreation(LocalDate.now());
            User savedProf = userRepository.save(prof);

            // Créer le profil professeur
            Professeur professeur = new Professeur();
            professeur.setUser(savedProf);
            professeur.setBureau("Bâtiment A, Bureau 12");
            professeur.setDepartement("Informatique");
            professeur.setDiscipline("Génie Logiciel");
            professeur.setSpecialite("Java / Spring");
            professeur.setResponsableFiliere(false);
            professeurRepository.save(professeur);
        }

        // --- Étudiant ---
        if (!userRepository.existsByEmail("etud.dupont@test.com")) {
            User etud = new User();
            etud.setEmail("etud.dupont@test.com");
            etud.setNom("Étudiant Dupont");
            etud.setMotDePasse(passwordEncoder.encode("default123"));
            etud.setRole(Role.ETUDIANT);
            etud.setDateCreation(LocalDate.now());
            User savedEtud = userRepository.save(etud);

            // Créer le profil étudiant
            Etudiant etudiant = new Etudiant();
            etudiant.setUser(savedEtud);
            etudiant.setMatricule("ETU-0001");
            etudiant.setCin("AB123456");
            etudiant.setCne("CNE12345");
            etudiant.setDateNaissance(LocalDate.of(2002, 5, 10));
            etudiant.setLieuNaissance("Casablanca");
            etudiant.setAdresse("123 Rue Exemple, Casablanca");
            etudiant.setTelephone("0612345678");
            etudiant.setSexe("M");
            etudiant.setNationalite("Marocaine");
            etudiant.setVille("Casablanca");
            // etudiant.setFiliere(null); // à définir si nécessaire
            etudiantRepository.save(etudiant);
        }
    }
}