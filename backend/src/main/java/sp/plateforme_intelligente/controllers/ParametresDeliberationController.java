package sp.plateforme_intelligente.controllers;

import sp.plateforme_intelligente.dto.ParametresDeliberationDTO;
import sp.plateforme_intelligente.models.ParametresDeliberation;
import sp.plateforme_intelligente.services.ParametresDeliberationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/parametres-deliberation")
@CrossOrigin(origins = "*")
public class ParametresDeliberationController {
    
    @Autowired
    private ParametresDeliberationService parametresService;
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ParametresDeliberationDTO>> getAllParametres() {
        return ResponseEntity.ok(parametresService.getAllParametres());
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ParametresDeliberationDTO> getParametresById(@PathVariable Long id) {
        return ResponseEntity.ok(parametresService.getParametresById(id));
    }
    
    @GetMapping("/filiere/{filiereId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ParametresDeliberationDTO> getParametresByFiliere(@PathVariable Long filiereId) {
        return ResponseEntity.ok(parametresService.getParametresByFiliere(filiereId));
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ParametresDeliberationDTO> createParametres(@RequestBody ParametresDeliberation params) {
        ParametresDeliberationDTO created = parametresService.createParametres(params);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ParametresDeliberationDTO> updateParametres(
            @PathVariable Long id,
            @RequestBody ParametresDeliberation params) {
        return ResponseEntity.ok(parametresService.updateParametres(id, params));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteParametres(@PathVariable Long id) {
        parametresService.deleteParametres(id);
        return ResponseEntity.ok().build();
    }
}