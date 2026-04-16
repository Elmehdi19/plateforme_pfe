package sp.plateforme_intelligente.controllers;

import sp.plateforme_intelligente.dto.InscriptionDTO;
import sp.plateforme_intelligente.models.Inscription;
import sp.plateforme_intelligente.services.InscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/inscriptions")
@CrossOrigin(origins = "*")
public class InscriptionController {
    
    @Autowired
    private InscriptionService inscriptionService;
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR')")
    public ResponseEntity<List<InscriptionDTO>> getAllInscriptions() {
        return ResponseEntity.ok(inscriptionService.getAllInscriptions());
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR')")
    public ResponseEntity<InscriptionDTO> getInscriptionById(@PathVariable Long id) {
        return ResponseEntity.ok(inscriptionService.getInscriptionById(id));
    }
    
    @GetMapping("/etudiant/{etudiantId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR') or hasRole('ETUDIANT')")
    public ResponseEntity<List<InscriptionDTO>> getInscriptionsByEtudiant(@PathVariable Long etudiantId) {
        return ResponseEntity.ok(inscriptionService.getInscriptionsByEtudiant(etudiantId));
    }
    
    @GetMapping("/annee-univ/{anneeUnivId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR')")
    public ResponseEntity<List<InscriptionDTO>> getInscriptionsByAnneeUniv(@PathVariable Long anneeUnivId) {
        return ResponseEntity.ok(inscriptionService.getInscriptionsByAnneeUniv(anneeUnivId));
    }
    
    @GetMapping("/semestre/{semestreId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR')")
    public ResponseEntity<List<InscriptionDTO>> getInscriptionsBySemestre(@PathVariable Long semestreId) {
        return ResponseEntity.ok(inscriptionService.getInscriptionsBySemestre(semestreId));
    }
    
    @GetMapping("/type/{type}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<InscriptionDTO>> getInscriptionsByType(@PathVariable String type) {
        return ResponseEntity.ok(inscriptionService.getInscriptionsByType(type));
    }
    
    @PostMapping("/en-ligne")
    public ResponseEntity<InscriptionDTO> inscriptionEnLigne(@RequestBody Inscription inscription) {
        InscriptionDTO created = inscriptionService.inscriptionEnLigne(inscription);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
    
    @PostMapping("/administrative/{etudiantId}/{anneeUnivId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<InscriptionDTO> inscriptionAdministrative(
            @PathVariable Long etudiantId,
            @PathVariable Long anneeUnivId,
            @RequestBody Inscription inscription) {
        InscriptionDTO created = inscriptionService.inscriptionAdministrative(etudiantId, anneeUnivId, inscription);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
    
    @PostMapping("/semestre/{etudiantId}/{semestreId}/{anneeUnivId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR')")
    public ResponseEntity<?> inscrireAuSemestre(
            @PathVariable Long etudiantId,
            @PathVariable Long semestreId,
            @PathVariable Long anneeUnivId) {
        inscriptionService.inscrireAuSemestre(etudiantId, semestreId, anneeUnivId);
        return ResponseEntity.ok("Inscription réussie");
    }
    
    @DeleteMapping("/semestre/{etudiantId}/{semestreId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR')")
    public ResponseEntity<?> desinscrireDuSemestre(
            @PathVariable Long etudiantId,
            @PathVariable Long semestreId) {
        inscriptionService.desinscrireDuSemestre(etudiantId, semestreId);
        return ResponseEntity.ok("Désinscription réussie");
    }
}