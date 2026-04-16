package sp.plateforme_intelligente.controllers;

import sp.plateforme_intelligente.dto.AnneeUniversitaireDTO;
import sp.plateforme_intelligente.models.AnneeUniversitaire;
import sp.plateforme_intelligente.services.AnneeUniversitaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/annees-universitaires")
@CrossOrigin(origins = "*")
public class AnneeUniversitaireController {
    
    @Autowired
    private AnneeUniversitaireService anneeUniversitaireService;
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR')")
    public ResponseEntity<List<AnneeUniversitaireDTO>> getAllAnneesUniversitaires() {
        return ResponseEntity.ok(anneeUniversitaireService.getAllAnneesUniversitaires());
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR')")
    public ResponseEntity<AnneeUniversitaireDTO> getAnneeUniversitaireById(@PathVariable Long id) {
        return ResponseEntity.ok(anneeUniversitaireService.getAnneeUniversitaireById(id));
    }
    
    @GetMapping("/code/{code}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR')")
    public ResponseEntity<AnneeUniversitaireDTO> getAnneeUniversitaireByCode(@PathVariable String code) {
        return ResponseEntity.ok(anneeUniversitaireService.getAnneeUniversitaireByCode(code));
    }
    
    @GetMapping("/active")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR')")
    public ResponseEntity<AnneeUniversitaireDTO> getAnneeUniversitaireActive() {
        return ResponseEntity.ok(anneeUniversitaireService.getAnneeUniversitaireActive());
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AnneeUniversitaireDTO> createAnneeUniversitaire(@RequestBody AnneeUniversitaire anneeUniv) {
        AnneeUniversitaireDTO created = anneeUniversitaireService.createAnneeUniversitaire(anneeUniv);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AnneeUniversitaireDTO> updateAnneeUniversitaire(
            @PathVariable Long id,
            @RequestBody AnneeUniversitaire anneeUniv) {
        return ResponseEntity.ok(anneeUniversitaireService.updateAnneeUniversitaire(id, anneeUniv));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteAnneeUniversitaire(@PathVariable Long id) {
        anneeUniversitaireService.deleteAnneeUniversitaire(id);
        return ResponseEntity.ok().build();
    }
}