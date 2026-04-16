package sp.plateforme_intelligente.controllers;

import sp.plateforme_intelligente.dto.SemestreDTO;
import sp.plateforme_intelligente.models.Semestre;
import sp.plateforme_intelligente.services.SemestreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/semestres")
@CrossOrigin(origins = "*")
public class SemestreController {
    
    @Autowired
    private SemestreService semestreService;
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR')")
    public ResponseEntity<List<SemestreDTO>> getAllSemestres() {
        return ResponseEntity.ok(semestreService.getAllSemestres());
    }

    @GetMapping("/filiere/{filiereId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSEUR')")
    public ResponseEntity<List<SemestreDTO>> getSemestresByFiliere(@PathVariable Long filiereId) {
        return ResponseEntity.ok(semestreService.getSemestresByFiliere(filiereId));
}
    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR')")
    public ResponseEntity<SemestreDTO> getSemestreById(@PathVariable Long id) {
        return ResponseEntity.ok(semestreService.getSemestreById(id));
    }
    
    @GetMapping("/annee/{anneeId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR')")
    public ResponseEntity<List<SemestreDTO>> getSemestresByAnnee(@PathVariable Long anneeId) {
        return ResponseEntity.ok(semestreService.getSemestresByAnnee(anneeId));
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SemestreDTO> createSemestre(@RequestBody Semestre semestre) {
        SemestreDTO created = semestreService.createSemestre(semestre);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SemestreDTO> updateSemestre(@PathVariable Long id, @RequestBody Semestre semestre) {
        return ResponseEntity.ok(semestreService.updateSemestre(id, semestre));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteSemestre(@PathVariable Long id) {
        semestreService.deleteSemestre(id);
        return ResponseEntity.ok().build();
    }
}