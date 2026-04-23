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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sp.plateforme_intelligente.dto.AbsenceDTO;
import sp.plateforme_intelligente.models.Absence;
import sp.plateforme_intelligente.services.AbsenceService;

@RestController
@RequestMapping("/api/absences")
@CrossOrigin(origins = "*")
public class AbsenceController {

    @Autowired
    private AbsenceService absenceService;

    @GetMapping("/etudiant/{etudiantId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSEUR', 'ETUDIANT')")
    public ResponseEntity<List<AbsenceDTO>> getAbsencesByEtudiant(@PathVariable Long etudiantId) {
        return ResponseEntity.ok(absenceService.getAbsencesByEtudiant(etudiantId));
    }

    @GetMapping("/etudiant/{etudiantId}/count")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSEUR', 'ETUDIANT')")
    public ResponseEntity<Long> getAbsenceCountByEtudiant(@PathVariable Long etudiantId) {
        return ResponseEntity.ok(absenceService.getAbsenceCountByEtudiant(etudiantId));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSEUR')")
    public ResponseEntity<AbsenceDTO> createAbsence(@RequestBody Absence absence) {
        AbsenceDTO created = absenceService.createAbsence(absence);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFESSEUR')")
    public ResponseEntity<?> deleteAbsence(@PathVariable Long id) {
        absenceService.deleteAbsence(id);
        return ResponseEntity.ok().build();
    }
}