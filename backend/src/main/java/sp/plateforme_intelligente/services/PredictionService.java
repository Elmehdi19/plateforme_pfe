package sp.plateforme_intelligente.services;

import sp.plateforme_intelligente.dto.PredictionDTO;
import sp.plateforme_intelligente.models.Prediction;
import sp.plateforme_intelligente.models.Etudiant;
import sp.plateforme_intelligente.repositories.PredictionRepository;
import sp.plateforme_intelligente.repositories.EtudiantRepository;
import sp.plateforme_intelligente.repositories.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PredictionService {
    
    @Autowired
    private PredictionRepository predictionRepository;
    
    @Autowired
    private EtudiantRepository etudiantRepository;
    
    @Autowired
    private NoteRepository noteRepository;
    
    public List<PredictionDTO> getAllPredictions() {
        return predictionRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    public PredictionDTO getPredictionById(Long id) {
        Prediction prediction = predictionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Prédiction non trouvée"));
        return convertToDTO(prediction);
    }
    
    public List<PredictionDTO> getPredictionsByEtudiant(Long etudiantId) {
        return predictionRepository.findByEtudiantId(etudiantId).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    public PredictionDTO getLastPredictionByEtudiant(Long etudiantId) {
        List<Prediction> predictions = predictionRepository.findLastByEtudiantId(etudiantId);
        if (predictions.isEmpty()) {
            return null;
        }
        return convertToDTO(predictions.get(0));
    }
    
    public PredictionDTO genererPrediction(Long etudiantId) {
        Etudiant etudiant = etudiantRepository.findById(etudiantId)
            .orElseThrow(() -> new RuntimeException("Étudiant non trouvé"));
        
        Float scoreRisque = calculerScoreRisque(etudiantId);
        String niveau = determinerNiveauRisque(scoreRisque);
        
        Prediction prediction = new Prediction();
        prediction.setEtudiant(etudiant);
        prediction.setScoreRisque(scoreRisque);
        prediction.setNiveauRisque(niveau);
        prediction.setDatePrediction(LocalDate.now());
        prediction.setModeleVersion("v1.0");
        
        Prediction saved = predictionRepository.save(prediction);
        return convertToDTO(saved);
    }
    
    public Map<String, Object> getStatistiquesPredictions() {
        Map<String, Object> stats = new HashMap<>();
        
        long total = predictionRepository.count();
        long risqueEleve = predictionRepository.countByNiveauRisque("ELEVE");
        long risqueMoyen = predictionRepository.countByNiveauRisque("MOYEN");
        long risqueFaible = predictionRepository.countByNiveauRisque("FAIBLE");
        
        stats.put("total", total);
        stats.put("risqueEleve", risqueEleve);
        stats.put("risqueMoyen", risqueMoyen);
        stats.put("risqueFaible", risqueFaible);
        
        if (total > 0) {
            stats.put("pourcentageEleve", (risqueEleve * 100.0) / total);
            stats.put("pourcentageMoyen", (risqueMoyen * 100.0) / total);
            stats.put("pourcentageFaible", (risqueFaible * 100.0) / total);
        }
        
        return stats;
    }
    
    public List<PredictionDTO> getRecentPredictions() {
        return predictionRepository.findRecentPredictions().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    private Float calculerScoreRisque(Long etudiantId) {
        Double moyenne = noteRepository.calculerMoyenneSemestre(etudiantId, null); // À adapter
        
        if (moyenne == null) {
            return 0.5f;
        }
        
        // Logique simple : plus la moyenne est basse, plus le risque est élevé
        if (moyenne >= 14) return 0.1f;
        if (moyenne >= 12) return 0.3f;
        if (moyenne >= 10) return 0.6f;
        return 0.9f;
    }
    
    private String determinerNiveauRisque(Float score) {
        if (score < 0.3) return "FAIBLE";
        if (score < 0.7) return "MOYEN";
        return "ELEVE";
    }
    
    private PredictionDTO convertToDTO(Prediction prediction) {
        PredictionDTO dto = new PredictionDTO();
        dto.setId(prediction.getId());
        dto.setScoreRisque(prediction.getScoreRisque());
        dto.setNiveauRisque(prediction.getNiveauRisque());
        dto.setDatePrediction(prediction.getDatePrediction());
        dto.setModeleVersion(prediction.getModeleVersion());
        
        if (prediction.getEtudiant() != null) {
            dto.setEtudiantId(prediction.getEtudiant().getId());
            dto.setEtudiantMatricule(prediction.getEtudiant().getMatricule());
            if (prediction.getEtudiant().getUser() != null) {
                dto.setEtudiantNom(prediction.getEtudiant().getUser().getNom());
            }
        }
        
        return dto;
    }
}