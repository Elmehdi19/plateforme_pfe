package sp.plateforme_intelligente.controllers;

import sp.plateforme_intelligente.services.DeliberationService;
import sp.plateforme_intelligente.services.NoteService;
import sp.plateforme_intelligente.repositories.ParametresDeliberationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/deliberation")
@CrossOrigin(origins = "*")
public class DeliberationController {
    
    @Autowired
    private DeliberationService deliberationService;
    
    @Autowired
    private NoteService noteService;
    
    @Autowired
    private ParametresDeliberationRepository parametresRepository;
    
    
    // ==================== RÉSULTATS PAR MODULE ====================
    
    /**
     * Résultat d'un module pour un étudiant
     */
    @GetMapping("/module/{moduleId}/etudiant/{etudiantId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR')")
    public ResponseEntity<?> resultatModule(
            @PathVariable Long moduleId,
            @PathVariable Long etudiantId,
            @RequestParam(required = false) Float noteMinModule) {
        
        // Si noteMinModule non fourni, on le récupère des paramètres
        if (noteMinModule == null) {
            // À adapter selon comment tu stockes les paramètres
            noteMinModule = 10.0f; // Valeur par défaut
        }
        
        Map<String, Object> resultat = noteService.calculerMoyenneModule(etudiantId, moduleId, noteMinModule);
        return ResponseEntity.ok(resultat);
    }
    
    /**
     * Résultat d'un module pour tous les étudiants d'une filière
     */
    @GetMapping("/module/{moduleId}/filiere/{filiereId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR')")
    public ResponseEntity<?> resultatModulePourFiliere(
            @PathVariable Long moduleId,
            @PathVariable Long filiereId,
            @RequestParam(required = false) Float noteMinModule) {
        
        if (noteMinModule == null) noteMinModule = 10.0f;
        
        // Cette méthode nécessite d'être ajoutée dans le service
        // Pour l'instant, retournons un message
        return ResponseEntity.ok(Map.of(
            "message", "À implémenter - Résultats du module pour tous les étudiants",
            "moduleId", moduleId,
            "filiereId", filiereId
        ));
    }
    
    // ==================== RÉSULTATS PAR SEMESTRE ====================
    
    /**
     * Résultat d'un semestre pour un étudiant
     */
    @GetMapping("/semestre/{semestreId}/etudiant/{etudiantId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR')")
    public ResponseEntity<?> resultatSemestre(
            @PathVariable Long semestreId,
            @PathVariable Long etudiantId,
            @RequestParam(required = false) Float moyenneMinSemestre,
            @RequestParam(required = false) Float noteMinModule,
            @RequestParam(required = false) Float noteEliminatoire) {
        
        // Valeurs par défaut si non fournies
        if (moyenneMinSemestre == null) moyenneMinSemestre = 10.0f;
        if (noteMinModule == null) noteMinModule = 10.0f;
        if (noteEliminatoire == null) noteEliminatoire = 5.0f;
        
        String resultat = deliberationService.resultatSemestre(
            semestreId, etudiantId, moyenneMinSemestre, noteMinModule, noteEliminatoire);
        
        Float moyenne = deliberationService.calculerMoyenneSemestre(semestreId, etudiantId);
        
        return ResponseEntity.ok(Map.of(
            "etudiantId", etudiantId,
            "semestreId", semestreId,
            "moyenne", moyenne != null ? moyenne : 0,
            "resultat", resultat
        ));
    }
    
    /**
     * Résultat d'un semestre pour tous les étudiants d'une filière
     */
    @GetMapping("/semestre/{semestreId}/filiere/{filiereId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR')")
    public ResponseEntity<?> resultatSemestrePourFiliere(
            @PathVariable Long semestreId,
            @PathVariable Long filiereId,
            @RequestParam(required = false) Float moyenneMinSemestre,
            @RequestParam(required = false) Float noteMinModule,
            @RequestParam(required = false) Float noteEliminatoire) {
        
        if (moyenneMinSemestre == null) moyenneMinSemestre = 10.0f;
        if (noteMinModule == null) noteMinModule = 10.0f;
        if (noteEliminatoire == null) noteEliminatoire = 5.0f;
        
        // Utiliser la méthode du service pour les statistiques
        Map<String, Object> statistiques = deliberationService.getStatistiquesSemestre(
            semestreId, filiereId, moyenneMinSemestre, noteMinModule, noteEliminatoire);
        
        return ResponseEntity.ok(statistiques);
    }
    
    // ==================== RÉSULTATS PAR ANNÉE ====================
    
    /**
     * Résultat annuel pour un étudiant
     */
    @GetMapping("/annee/{anneeId}/etudiant/{etudiantId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR')")
    public ResponseEntity<?> resultatAnnuel(
            @PathVariable Long anneeId,
            @PathVariable Long etudiantId,
            @RequestParam(required = false) Float moyenneMinAnnuelle,
            @RequestParam(required = false) Float moyenneMinSemestre,
            @RequestParam(required = false) Float noteMinModule,
            @RequestParam(required = false) Float noteEliminatoire) {
        
        // Valeurs par défaut
        if (moyenneMinAnnuelle == null) moyenneMinAnnuelle = 10.0f;
        if (moyenneMinSemestre == null) moyenneMinSemestre = 10.0f;
        if (noteMinModule == null) noteMinModule = 10.0f;
        if (noteEliminatoire == null) noteEliminatoire = 5.0f;
        
        String resultat = deliberationService.resultatAnnuel(
            anneeId, etudiantId, moyenneMinAnnuelle, moyenneMinSemestre, 
            noteMinModule, noteEliminatoire);
        
        Float moyenneAnnee = deliberationService.calculerMoyenneAnnee(anneeId, etudiantId);
        
        return ResponseEntity.ok(Map.of(
            "etudiantId", etudiantId,
            "anneeId", anneeId,
            "moyenne", moyenneAnnee != null ? moyenneAnnee : 0,
            "resultat", resultat
        ));
    }
    
    // ==================== GÉNÉRATION DE PV ====================
    
    /**
     * Génère le PV d'un module
     */
    @GetMapping("/module/{moduleId}/pv")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR')")
    public ResponseEntity<?> genererPVModule(
            @PathVariable Long moduleId,
            @RequestParam(defaultValue = "ORDINAIRE") String session,
            @RequestParam(required = false) Float noteMinModule) {
        
        if (noteMinModule == null) noteMinModule = 10.0f;
        
        List<Map<String, Object>> pv = deliberationService.genererPVModule(moduleId, session, noteMinModule);
        
        return ResponseEntity.ok(Map.of(
            "moduleId", moduleId,
            "session", session,
            "nbEtudiants", pv.size(),
            "resultats", pv
        ));
    }
    
    /**
     * Génère le PV d'un semestre
     */
    @GetMapping("/semestre/{semestreId}/pv")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR')")
    public ResponseEntity<?> genererPVSemestre(
            @PathVariable Long semestreId,
            @RequestParam Long filiereId,
            @RequestParam(required = false) Float moyenneMinSemestre,
            @RequestParam(required = false) Float noteMinModule,
            @RequestParam(required = false) Float noteEliminatoire) {
        
        if (moyenneMinSemestre == null) moyenneMinSemestre = 10.0f;
        if (noteMinModule == null) noteMinModule = 10.0f;
        if (noteEliminatoire == null) noteEliminatoire = 5.0f;
        
        List<Map<String, Object>> pv = deliberationService.genererPVSemestre(
            semestreId, filiereId, moyenneMinSemestre, noteMinModule, noteEliminatoire);
        
        return ResponseEntity.ok(Map.of(
            "semestreId", semestreId,
            "filiereId", filiereId,
            "nbEtudiants", pv.size(),
            "resultats", pv
        ));
    }
    
    // ==================== STATISTIQUES ====================
    
    /**
     * Statistiques de délibération pour un semestre
     */
    @GetMapping("/statistiques/semestre/{semestreId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR')")
    public ResponseEntity<?> getStatistiquesSemestre(
            @PathVariable Long semestreId,
            @RequestParam Long filiereId,
            @RequestParam(required = false) Float moyenneMinSemestre,
            @RequestParam(required = false) Float noteMinModule,
            @RequestParam(required = false) Float noteEliminatoire) {
        
        if (moyenneMinSemestre == null) moyenneMinSemestre = 10.0f;
        if (noteMinModule == null) noteMinModule = 10.0f;
        if (noteEliminatoire == null) noteEliminatoire = 5.0f;
        
        Map<String, Object> stats = deliberationService.getStatistiquesSemestre(
            semestreId, filiereId, moyenneMinSemestre, noteMinModule, noteEliminatoire);
        
        return ResponseEntity.ok(stats);
    }
    
    /**
     * Moyenne d'une filière pour un semestre
     */
    @GetMapping("/moyenne/filiere/{filiereId}/semestre/{semestreId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR')")
    public ResponseEntity<?> getMoyenneFiliere(
            @PathVariable Long filiereId,
            @PathVariable Long semestreId) {
        
        Map<String, Object> resultat = deliberationService.calculerMoyenneFiliere(filiereId, semestreId);
        
        return ResponseEntity.ok(resultat);
    }
    
    /**
     * Étudiants en risque d'échec
     */
    @GetMapping("/risque")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR')")
    public ResponseEntity<?> getEtudiantsEnRisque(
            @RequestParam Long filiereId,
            @RequestParam Long semestreId,
            @RequestParam(defaultValue = "8.0") Float noteSeuil) {
        
        List<Map<String, Object>> etudiants = deliberationService.getEtudiantsEnRisque(
            filiereId, semestreId, noteSeuil);
        
        return ResponseEntity.ok(Map.of(
            "filiereId", filiereId,
            "semestreId", semestreId,
            "noteSeuil", noteSeuil,
            "nbEtudiants", etudiants.size(),
            "etudiants", etudiants
        ));
    }
    
    // ==================== PARAMÈTRES ====================
    
    /**
     * Récupère les paramètres de délibération pour une filière
     */
    @GetMapping("/parametres/{filiereId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR')")
    public ResponseEntity<?> getParametresDeliberation(@PathVariable Long filiereId) {
        
        return parametresRepository.findByFiliereIdAndActifTrue(filiereId)
            .map(parametres -> {
                Map<String, Object> result = new HashMap<>();
                result.put("filiereId", filiereId);
                result.put("noteMinModule", parametres.getNoteMinModule());
                result.put("moyenneMinSemestre", parametres.getMoyenneMinSemestre());
                result.put("noteEliminatoire", parametres.getNoteEliminatoire());
                result.put("moyenneMinAnnuelle", parametres.getMoyenneMinAnnuelle());
                result.put("maxModulesParSemestre", parametres.getMaxModulesParSemestre());
                return ResponseEntity.ok(result);
            })
            .orElseGet(() -> {
                Map<String, Object> result = new HashMap<>();
                result.put("filiereId", filiereId);
                result.put("message", "Paramètres par défaut");
                result.put("noteMinModule", 10.0f);
                result.put("moyenneMinSemestre", 10.0f);
                result.put("noteEliminatoire", 5.0f);
                result.put("moyenneMinAnnuelle", 10.0f);
                result.put("maxModulesParSemestre", 7);
                return ResponseEntity.ok(result);
            });
    }
}