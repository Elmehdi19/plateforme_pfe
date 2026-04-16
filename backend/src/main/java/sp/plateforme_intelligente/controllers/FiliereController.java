package sp.plateforme_intelligente.controllers;

import sp.plateforme_intelligente.dto.FiliereDTO;
import sp.plateforme_intelligente.models.Filiere;
import sp.plateforme_intelligente.services.FiliereService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/filieres")
@CrossOrigin(origins = "*")
public class FiliereController {
    
    @Autowired
    private FiliereService filiereService;
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR')")
    public ResponseEntity<List<FiliereDTO>> getAllFilieres() {
        return ResponseEntity.ok(filiereService.getAllFilieres());
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR')")
    public ResponseEntity<FiliereDTO> getFiliereById(@PathVariable Long id) {
        return ResponseEntity.ok(filiereService.getFiliereById(id));
    }
    
    @GetMapping("/departement/{departementId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR')")
    public ResponseEntity<List<FiliereDTO>> getFilieresByDepartement(@PathVariable Long departementId) {
        return ResponseEntity.ok(filiereService.getFilieresByDepartement(departementId));
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FiliereDTO> createFiliere(@RequestBody Filiere filiere) {
        FiliereDTO created = filiereService.createFiliere(filiere);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FiliereDTO> updateFiliere(@PathVariable Long id, @RequestBody Filiere filiere) {
        return ResponseEntity.ok(filiereService.updateFiliere(id, filiere));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteFiliere(@PathVariable Long id) {
        filiereService.deleteFiliere(id);
        return ResponseEntity.ok().build();
    }
}