package sp.plateforme_intelligente.controllers;

import sp.plateforme_intelligente.dto.PredictionDTO;
import sp.plateforme_intelligente.services.PredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/predictions")
@CrossOrigin(origins = "*")
public class PredictionController {
    
    @Autowired
    private PredictionService predictionService;
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR')")
    public ResponseEntity<List<PredictionDTO>> getAllPredictions() {
        return ResponseEntity.ok(predictionService.getAllPredictions());
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR')")
    public ResponseEntity<PredictionDTO> getPredictionById(@PathVariable Long id) {
        return ResponseEntity.ok(predictionService.getPredictionById(id));
    }
    
    @GetMapping("/etudiant/{etudiantId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR') or hasRole('ETUDIANT')")
    public ResponseEntity<List<PredictionDTO>> getPredictionsByEtudiant(@PathVariable Long etudiantId) {
        return ResponseEntity.ok(predictionService.getPredictionsByEtudiant(etudiantId));
    }
    
    @GetMapping("/etudiant/{etudiantId}/last")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR') or hasRole('ETUDIANT')")
    public ResponseEntity<PredictionDTO> getLastPredictionByEtudiant(@PathVariable Long etudiantId) {
        PredictionDTO prediction = predictionService.getLastPredictionByEtudiant(etudiantId);
        if (prediction == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(prediction);
    }
    
    @PostMapping("/generer/{etudiantId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR')")
    public ResponseEntity<PredictionDTO> genererPrediction(@PathVariable Long etudiantId) {
        return ResponseEntity.ok(predictionService.genererPrediction(etudiantId));
    }
    
    @GetMapping("/statistiques")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR')")
    public ResponseEntity<Map<String, Object>> getStatistiquesPredictions() {
        return ResponseEntity.ok(predictionService.getStatistiquesPredictions());
    }
    
    @GetMapping("/recentes")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR')")
    public ResponseEntity<List<PredictionDTO>> getRecentPredictions() {
        return ResponseEntity.ok(predictionService.getRecentPredictions());
    }
}