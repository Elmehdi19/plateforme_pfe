package sp.plateforme_intelligente.models;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "predictions")
public class Prediction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "etudiant_id", nullable = false)
    private Etudiant etudiant;
    
    private Float scoreRisque;
    
    private String niveauRisque; // "FAIBLE", "MOYEN", "ELEVE"
    
    private LocalDate datePrediction;
    
    private String modeleVersion;

    public Prediction() {}

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Etudiant getEtudiant() { return etudiant; }
    public void setEtudiant(Etudiant etudiant) { this.etudiant = etudiant; }
    public Float getScoreRisque() { return scoreRisque; }
    public void setScoreRisque(Float scoreRisque) { this.scoreRisque = scoreRisque; }
    public String getNiveauRisque() { return niveauRisque; }
    public void setNiveauRisque(String niveauRisque) { this.niveauRisque = niveauRisque; }
    public LocalDate getDatePrediction() { return datePrediction; }
    public void setDatePrediction(LocalDate datePrediction) { this.datePrediction = datePrediction; }
    public String getModeleVersion() { return modeleVersion; }
    public void setModeleVersion(String modeleVersion) { this.modeleVersion = modeleVersion; }
}