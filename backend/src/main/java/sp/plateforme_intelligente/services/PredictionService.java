package sp.plateforme_intelligente.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import sp.plateforme_intelligente.dto.PredictionDTO;
import sp.plateforme_intelligente.ml.LogisticRegression;
import sp.plateforme_intelligente.models.Etudiant;
import sp.plateforme_intelligente.models.Prediction;
import sp.plateforme_intelligente.repositories.EtudiantRepository;
import sp.plateforme_intelligente.repositories.NoteRepository;
import sp.plateforme_intelligente.repositories.PredictionRepository;

@Service
public class PredictionService {

    @Autowired
    private PredictionRepository predictionRepository;

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private NoteRepository noteRepository;

    private LogisticRegression model;

    @PostConstruct
    public void init() {
        // Liste des chemins possibles (ordre de priorité)
        String[] paths = {
            "src/main/resources/ml/logistic_model.ser",   // développement (backend/)
            "target/classes/ml/logistic_model.ser",      // après compilation
            "ml/logistic_model.ser",                     // exécution depuis backend
            "logistic_model.ser"                         // répertoire courant
        };

        for (String path : paths) {
            File file = new File(path);
            if (file.exists()) {
                try (InputStream is = new FileInputStream(file)) {
                    try (ObjectInputStream ois = new ObjectInputStream(is)) {
                        model = (LogisticRegression) ois.readObject();
                        System.out.println("✅ Modèle ML chargé depuis : " + file.getAbsolutePath());
                        return;
                    }
                } catch (Exception e) {
                    System.err.println("Erreur lecture modèle depuis " + path + " : " + e.getMessage());
                }
            }
        }

        // Fallback : essayer depuis le classpath
        try (InputStream is = getClass().getResourceAsStream("/ml/logistic_model.ser")) {
            if (is != null) {
                try (ObjectInputStream ois = new ObjectInputStream(is)) {
                    model = (LogisticRegression) ois.readObject();
                    System.out.println("✅ Modèle ML chargé depuis le classpath.");
                    return;
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur chargement modèle depuis classpath : " + e.getMessage());
        }

        System.err.println("⚠️ Modèle ML non trouvé. Les prédictions utiliseront la logique par défaut.");
    }

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
        if (predictions.isEmpty()) return null;
        return convertToDTO(predictions.get(0));
    }

    public PredictionDTO genererPrediction(Long etudiantId) {
        Etudiant etudiant = etudiantRepository.findById(etudiantId)
                .orElseThrow(() -> new RuntimeException("Étudiant non trouvé"));

        double[] features = extractFeatures(etudiantId);
        float scoreRisque;

        if (model != null) {
            double proba = model.predictProbability(features);
            scoreRisque = (float) proba;
        } else {
            // Logique par défaut basée sur la moyenne
            Double moyenne = features[0];
            if (moyenne == null || moyenne < 10) scoreRisque = 0.8f;
            else if (moyenne < 12) scoreRisque = 0.5f;
            else scoreRisque = 0.2f;
        }

        String niveau = determinerNiveauRisque(scoreRisque);

        Prediction prediction = new Prediction();
        prediction.setEtudiant(etudiant);
        prediction.setScoreRisque(scoreRisque);
        prediction.setNiveauRisque(niveau);
        prediction.setDatePrediction(LocalDate.now());
        prediction.setModeleVersion(model != null ? "LogisticRegression-v1" : "DefaultRules");

        Prediction saved = predictionRepository.save(prediction);
        return convertToDTO(saved);
    }

    private double[] extractFeatures(Long etudiantId) {
        Double moyenne = noteRepository.calculerMoyenneGenerale(etudiantId);
        if (moyenne == null) moyenne = 0.0;
        int nbAbsences = 0; // À remplacer par une vraie requête si disponible
        int modulesEchoues = noteRepository.countModulesEchoues(etudiantId, 10.0f);
        return new double[]{moyenne, nbAbsences, modulesEchoues};
    }

    private String determinerNiveauRisque(Float score) {
        if (score < 0.3) return "FAIBLE";
        if (score < 0.7) return "MOYEN";
        return "ELEVE";
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