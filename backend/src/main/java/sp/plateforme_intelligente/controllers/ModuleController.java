package sp.plateforme_intelligente.controllers;

import sp.plateforme_intelligente.dto.ModuleDTO;
import sp.plateforme_intelligente.models.Module;
import sp.plateforme_intelligente.services.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/modules")
@CrossOrigin(origins = "*")
public class ModuleController {
    
    @Autowired
    private ModuleService moduleService;
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR')")
    public ResponseEntity<List<ModuleDTO>> getAllModules() {
        return ResponseEntity.ok(moduleService.getAllModules());
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR')")
    public ResponseEntity<ModuleDTO> getModuleById(@PathVariable Long id) {
        return ResponseEntity.ok(moduleService.getModuleById(id));
    }
    
    @GetMapping("/semestre/{semestreId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR')")
    public ResponseEntity<List<ModuleDTO>> getModulesBySemestre(@PathVariable Long semestreId) {
        return ResponseEntity.ok(moduleService.getModulesBySemestre(semestreId));
    }
    
    @GetMapping("/professeur/{professeurId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR')")
    public ResponseEntity<List<ModuleDTO>> getModulesByProfesseur(@PathVariable Long professeurId) {
        return ResponseEntity.ok(moduleService.getModulesByProfesseur(professeurId));
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ModuleDTO> createModule(@RequestBody Module module) {
        ModuleDTO created = moduleService.createModule(module);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ModuleDTO> updateModule(@PathVariable Long id, @RequestBody Module module) {
        return ResponseEntity.ok(moduleService.updateModule(id, module));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteModule(@PathVariable Long id) {
        moduleService.deleteModule(id);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/recherche")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR')")
    public ResponseEntity<List<ModuleDTO>> rechercherModules(@RequestParam String keyword) {
        return ResponseEntity.ok(moduleService.rechercherModules(keyword));
    }
}