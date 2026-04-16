package sp.plateforme_intelligente.dto;

import java.time.LocalDate;

public class PredictionDTO {
    private Long id;
    private Long etudiantId;
    private String etudiantNom;
    private String etudiantMatricule;
    private Float scoreRisque;
    private String niveauRisque;
    private LocalDate datePrediction;
    private String modeleVersion;
    
    public PredictionDTO() {}
    
    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getEtudiantId() { return etudiantId; }
    public void setEtudiantId(Long etudiantId) { this.etudiantId = etudiantId; }
    
    public String getEtudiantNom() { return etudiantNom; }
    public void setEtudiantNom(String etudiantNom) { this.etudiantNom = etudiantNom; }
    
    public String getEtudiantMatricule() { return etudiantMatricule; }
    public void setEtudiantMatricule(String etudiantMatricule) { this.etudiantMatricule = etudiantMatricule; }
    
    public Float getScoreRisque() { return scoreRisque; }
    public void setScoreRisque(Float scoreRisque) { this.scoreRisque = scoreRisque; }
    
    public String getNiveauRisque() { return niveauRisque; }
    public void setNiveauRisque(String niveauRisque) { this.niveauRisque = niveauRisque; }
    
    public LocalDate getDatePrediction() { return datePrediction; }
    public void setDatePrediction(LocalDate datePrediction) { this.datePrediction = datePrediction; }
    
    public String getModeleVersion() { return modeleVersion; }
    public void setModeleVersion(String modeleVersion) { this.modeleVersion = modeleVersion; }
}