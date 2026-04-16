package sp.plateforme_intelligente.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sp.plateforme_intelligente.dto.StatistiquesDTO;
import sp.plateforme_intelligente.services.StatistiquesService;

@RestController
@RequestMapping("/api/statistiques")
@CrossOrigin(origins = "*")
public class StatistiquesController {
    
    @Autowired
    private StatistiquesService statistiquesService;
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR')")
    public ResponseEntity<StatistiquesDTO> getStatistiques() {
        return ResponseEntity.ok(statistiquesService.getStatistiques());
    }
    @GetMapping("/moyennes-modules")
@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<?> getMoyennesModules() {
    // Logique pour récupérer les moyennes par module
    Map<String, Object> data = new HashMap<>();
    data.put("labels", new String[]{"Module 1", "Module 2", "Module 3"});
    data.put("values", new double[]{12.5, 14.2, 10.8});
    return ResponseEntity.ok(data);
}

@GetMapping("/decisions")
@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<?> getDecisions() {
    // Logique pour récupérer les statistiques de décisions
    Map<String, Object> data = new HashMap<>();
    data.put("labels", new String[]{"Validé", "Compensé", "Non validé"});
    data.put("values", new int[]{65, 20, 15});
    return ResponseEntity.ok(data);
}
}