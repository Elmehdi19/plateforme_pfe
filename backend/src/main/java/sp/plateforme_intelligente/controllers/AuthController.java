package sp.plateforme_intelligente.controllers;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sp.plateforme_intelligente.dto.ChangePasswordRequest;
import sp.plateforme_intelligente.dto.LoginRequest;
import sp.plateforme_intelligente.dto.LoginResponse;
import sp.plateforme_intelligente.dto.RegisterRequest;
import sp.plateforme_intelligente.models.Role;
import sp.plateforme_intelligente.models.User;
import sp.plateforme_intelligente.repositories.EtudiantRepository;
import sp.plateforme_intelligente.repositories.ProfesseurRepository;
import sp.plateforme_intelligente.repositories.UserRepository;
import sp.plateforme_intelligente.security.JwtUtils;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private ProfesseurRepository professeurRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow();

        Long etudiantId = null;
        Long professeurId = null;

        // Comparer avec les valeurs majuscules de l'enum
        if (user.getRole() == Role.ETUDIANT) {
            etudiantId = etudiantRepository.findByUser(user)
                           .map(etudiant -> etudiant.getId())
                           .orElse(null);
        } else if (user.getRole() == Role.PROFESSEUR) {
            professeurId = professeurRepository.findByUser(user)
                           .map(professeur -> professeur.getId())
                           .orElse(null);
        }
        // Pour admin, les deux restent null

        LoginResponse response = new LoginResponse();
        response.setToken(jwt);
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setNom(user.getNom());
        response.setRole(user.getRole().toString()); // retourne "ADMIN", "PROFESSEUR", "ETUDIANT"
        response.setEtudiantId(etudiantId);
        response.setProfesseurId(professeurId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity.badRequest().body("Email déjà utilisé !");
        }

        User user = new User();
        user.setNom(registerRequest.getNom());
        user.setEmail(registerRequest.getEmail());
        user.setMotDePasse(passwordEncoder.encode(registerRequest.getPassword()));

        try {
            // Convertir le rôle en majuscules avant de le parser
            user.setRole(Role.valueOf(registerRequest.getRole().toUpperCase()));
        } catch (IllegalArgumentException e) {
            user.setRole(Role.ETUDIANT); // valeur par défaut en majuscule
        }

        user.setDateCreation(LocalDate.now());
        userRepository.save(user);

        return ResponseEntity.ok("Utilisateur créé avec succès !");
    }
    @PutMapping("/change-password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request) {
        // Récupérer l'utilisateur connecté
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        
        // Vérifier l'ancien mot de passe
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getMotDePasse())) {
            return ResponseEntity.badRequest().body("Ancien mot de passe incorrect");
        }
        
        // Mettre à jour
        user.setMotDePasse(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        
        return ResponseEntity.ok("Mot de passe modifié avec succès");
    }
}