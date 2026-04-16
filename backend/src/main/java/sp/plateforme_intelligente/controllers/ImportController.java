package sp.plateforme_intelligente.controllers;

import sp.plateforme_intelligente.dto.*;
import sp.plateforme_intelligente.services.ImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/import")
@CrossOrigin(origins = "*")
public class ImportController {
    
    @Autowired
    private ImportService importService;
    
    @PostMapping("/etudiants")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR')")
    public ResponseEntity<?> importEtudiants(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("Fichier vide");
            }
            
            String fileName = file.getOriginalFilename();
            if (fileName == null || (!fileName.endsWith(".xlsx") && !fileName.endsWith(".xls"))) {
                return ResponseEntity.badRequest().body("Format de fichier non supporté. Utilisez .xlsx ou .xls");
            }
            
            ImportResultDTO result = importService.importEtudiants(file);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erreur lors de l'import: " + e.getMessage());
        }
    }
    
    @PostMapping("/professeurs")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> importProfesseurs(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("Fichier vide");
            }
            
            String fileName = file.getOriginalFilename();
            if (fileName == null || (!fileName.endsWith(".xlsx") && !fileName.endsWith(".xls"))) {
                return ResponseEntity.badRequest().body("Format de fichier non supporté. Utilisez .xlsx ou .xls");
            }
            
            ImportProfesseurResultDTO result = importService.importProfesseurs(file);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erreur lors de l'import: " + e.getMessage());
        }
    }
    
    @PostMapping("/filieres")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> importFilieres(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("Fichier vide");
            }
            
            String fileName = file.getOriginalFilename();
            if (fileName == null || (!fileName.endsWith(".xlsx") && !fileName.endsWith(".xls"))) {
                return ResponseEntity.badRequest().body("Format de fichier non supporté. Utilisez .xlsx ou .xls");
            }
            
            ImportFiliereResultDTO result = importService.importFilieres(file);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erreur lors de l'import: " + e.getMessage());
        }
    }
    
    @PostMapping("/modules")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> importModules(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("Fichier vide");
            }
            
            String fileName = file.getOriginalFilename();
            if (fileName == null || (!fileName.endsWith(".xlsx") && !fileName.endsWith(".xls"))) {
                return ResponseEntity.badRequest().body("Format de fichier non supporté. Utilisez .xlsx ou .xls");
            }
            
            ImportModuleResultDTO result = importService.importModules(file);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erreur lors de l'import: " + e.getMessage());
        }
    }
    
    @PostMapping("/elements")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> importElements(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("Fichier vide");
            }
            
            String fileName = file.getOriginalFilename();
            if (fileName == null || (!fileName.endsWith(".xlsx") && !fileName.endsWith(".xls"))) {
                return ResponseEntity.badRequest().body("Format de fichier non supporté. Utilisez .xlsx ou .xls");
            }
            
            ImportElementResultDTO result = importService.importElements(file);
            return ResponseEntity.ok(result);
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erreur lors de l'import: " + e.getMessage());
        }
    }
}