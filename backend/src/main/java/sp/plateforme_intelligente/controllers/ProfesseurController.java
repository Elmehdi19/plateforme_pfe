package sp.plateforme_intelligente.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sp.plateforme_intelligente.dto.ProfesseurDTO;
import sp.plateforme_intelligente.models.Professeur;
import sp.plateforme_intelligente.services.ProfesseurService;

@RestController
@RequestMapping("/api/professeurs")
@CrossOrigin(origins = "*")
public class ProfesseurController {

    @Autowired
    private ProfesseurService professeurService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ProfesseurDTO>> getAllProfesseurs() {
        return ResponseEntity.ok(professeurService.getAllProfesseurs());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProfesseurDTO> getProfesseurById(@PathVariable Long id) {
        return ResponseEntity.ok(professeurService.getProfesseurById(id));
    }

    @GetMapping("/responsables")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ProfesseurDTO>> getResponsablesFiliere() {
        return ResponseEntity.ok(professeurService.getResponsablesFiliere());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProfesseurDTO> createProfesseur(@RequestBody Professeur professeur) {
        ProfesseurDTO created = professeurService.createProfesseur(professeur);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProfesseurDTO> updateProfesseur(@PathVariable Long id, @RequestBody Professeur professeur) {
        return ResponseEntity.ok(professeurService.updateProfesseur(id, professeur));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteProfesseur(@PathVariable Long id) {
        professeurService.deleteProfesseur(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/recherche")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ProfesseurDTO>> rechercherProfesseurs(@RequestParam String keyword) {
        return ResponseEntity.ok(professeurService.rechercherProfesseurs(keyword));
    }
}