package sp.plateforme_intelligente.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import sp.plateforme_intelligente.dto.ElementDTO;
import sp.plateforme_intelligente.models.Element;
import sp.plateforme_intelligente.services.ElementService;

@RestController
@RequestMapping("/api/elements")
@CrossOrigin(origins = "*")
public class ElementController {
    
    @Autowired
    private ElementService elementService;
    
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSEUR', 'ETUDIANT')")
    public ResponseEntity<List<ElementDTO>> getAllElements() {
        return ResponseEntity.ok(elementService.getAllElements());
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSEUR', 'ETUDIANT')")
    public ResponseEntity<ElementDTO> getElementById(@PathVariable Long id) {
        return ResponseEntity.ok(elementService.getElementById(id));
    }
    
    @GetMapping("/module/{moduleId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSEUR', 'ETUDIANT')")
    public ResponseEntity<List<ElementDTO>> getElementsByModule(@PathVariable Long moduleId) {
        return ResponseEntity.ok(elementService.getElementsByModule(moduleId));
    }
    
    @GetMapping("/professeur/{professeurId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSEUR', 'ETUDIANT')")
    public ResponseEntity<List<ElementDTO>> getElementsByProfesseur(@PathVariable Long professeurId) {
        return ResponseEntity.ok(elementService.getElementsByProfesseur(professeurId));
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ElementDTO> createElement(@RequestBody Element element) {
        ElementDTO created = elementService.createElement(element);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ElementDTO> updateElement(@PathVariable Long id, @RequestBody Element element) {
        return ResponseEntity.ok(elementService.updateElement(id, element));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteElement(@PathVariable Long id) {
        elementService.deleteElement(id);
        return ResponseEntity.ok().build();
    }
}