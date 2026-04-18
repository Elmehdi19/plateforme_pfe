package sp.plateforme_intelligente.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import sp.plateforme_intelligente.models.Role;
import sp.plateforme_intelligente.models.User;
import sp.plateforme_intelligente.repositories.UserRepository;
import java.time.LocalDate;

@Component
public class DataInitializer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @EventListener(ApplicationReadyEvent.class)
    public void initUsers() {
        if (!userRepository.existsByEmail("admin@test.com")) {
            User admin = new User();
            admin.setEmail("admin@test.com");
            admin.setNom("Admin");
            admin.setMotDePasse(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ADMIN);
            admin.setDateCreation(LocalDate.now());
            userRepository.save(admin);
        }

        if (!userRepository.existsByEmail("prof.dupont@test.com")) {
            User prof = new User();
            prof.setEmail("prof.dupont@test.com");
            prof.setNom("Professeur Dupont");
            prof.setMotDePasse(passwordEncoder.encode("default123"));
            prof.setRole(Role.PROFESSEUR);
            prof.setDateCreation(LocalDate.now());
            userRepository.save(prof);
        }

        if (!userRepository.existsByEmail("etud.dupont@test.com")) {
            User etud = new User();
            etud.setEmail("etud.dupont@test.com");
            etud.setNom("Étudiant Dupont");
            etud.setMotDePasse(passwordEncoder.encode("default123"));
            etud.setRole(Role.ETUDIANT);
            etud.setDateCreation(LocalDate.now());
            userRepository.save(etud);
        }
    }
}
