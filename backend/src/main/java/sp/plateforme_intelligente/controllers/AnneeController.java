package sp.plateforme_intelligente.controllers;

import sp.plateforme_intelligente.dto.AnneeDTO;
import sp.plateforme_intelligente.models.Annee;
import sp.plateforme_intelligente.services.AnneeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/annees")
@CrossOrigin(origins = "*")
public class AnneeController {
    
    @Autowired
    private AnneeService anneeService;
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR')")
    public ResponseEntity<List<AnneeDTO>> getAllAnnees() {
        return ResponseEntity.ok(anneeService.getAllAnnees());
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR')")
    public ResponseEntity<AnneeDTO> getAnneeById(@PathVariable Long id) {
        return ResponseEntity.ok(anneeService.getAnneeById(id));
    }
    
    @GetMapping("/filiere/{filiereId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR')")
    public ResponseEntity<List<AnneeDTO>> getAnneesByFiliere(@PathVariable Long filiereId) {
        return ResponseEntity.ok(anneeService.getAnneesByFiliere(filiereId));
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AnneeDTO> createAnnee(@RequestBody Annee annee) {
        AnneeDTO created = anneeService.createAnnee(annee);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AnneeDTO> updateAnnee(@PathVariable Long id, @RequestBody Annee annee) {
        return ResponseEntity.ok(anneeService.updateAnnee(id, annee));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteAnnee(@PathVariable Long id) {
        anneeService.deleteAnnee(id);
        return ResponseEntity.ok().build();
    }
}