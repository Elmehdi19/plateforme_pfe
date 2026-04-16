package sp.plateforme_intelligente.controllers;

import sp.plateforme_intelligente.dto.EtablissementDTO;
import sp.plateforme_intelligente.models.Etablissement;
import sp.plateforme_intelligente.services.EtablissementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/etablissements")
@CrossOrigin(origins = "*")
public class EtablissementController {

    @Autowired
    private EtablissementService etablissementService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSEUR')")
    public ResponseEntity<List<EtablissementDTO>> getAllEtablissements() {
        return ResponseEntity.ok(etablissementService.getAllEtablissements());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSEUR')")
    public ResponseEntity<EtablissementDTO> getEtablissementById(@PathVariable Long id) {
        return ResponseEntity.ok(etablissementService.getEtablissementById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EtablissementDTO> createEtablissement(@RequestBody Etablissement etablissement) {
        EtablissementDTO created = etablissementService.createEtablissement(etablissement);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EtablissementDTO> updateEtablissement(@PathVariable Long id, @RequestBody Etablissement etablissement) {
        return ResponseEntity.ok(etablissementService.updateEtablissement(id, etablissement));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteEtablissement(@PathVariable Long id) {
        etablissementService.deleteEtablissement(id);
        return ResponseEntity.ok().build();
    }
}
